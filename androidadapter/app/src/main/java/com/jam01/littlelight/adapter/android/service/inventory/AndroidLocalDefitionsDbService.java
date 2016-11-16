package com.jam01.littlelight.adapter.android.service.inventory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.google.gson.Gson;
import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.service.inventory.LocalDefinitionsDbService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by jam01 on 11/3/16.
 */
public class AndroidLocalDefitionsDbService implements LocalDefinitionsDbService {
    private final Context mContext;
    private final DestinyApi bungieApi;
    private final String TAG = this.getClass().getSimpleName();
    private Future<SQLiteDatabase> database;
    private List<ItemDefinition> exotics;
    private Gson gson = new Gson();

    public AndroidLocalDefitionsDbService(Context context, DestinyApi destinyApi) {
        this.mContext = context;
        this.bungieApi = destinyApi;

        database = dbFuture();
    }

    private SQLiteDatabase getDatabase() {
        //If we've already tried then return it or get it again if it failed because of a network exception
        if (database.isDone()) {
            try {
                return database.get();
            } catch (InterruptedException | ExecutionException e) {
                if (e.getCause() instanceof IllegalNetworkStateException) {
                    database = dbFuture();
                    return getDatabase();
                } else throw new IllegalStateException(e);
            }
        }
        // If we haven't tried then return a new one or rethrow the network exception
        else {
            try {
                return database.get();
            } catch (InterruptedException | ExecutionException e) {
                if (e.getCause() instanceof IllegalNetworkStateException) {
                    throw new IllegalNetworkStateException(e.getCause().getCause().getMessage(), e.getCause().getCause());
                } else throw new IllegalStateException(e);
            }
        }
    }

    private Future<SQLiteDatabase> dbFuture() {
        return Executors.newCachedThreadPool().submit(new Callable<SQLiteDatabase>() {
            @Override
            public SQLiteDatabase call() throws Exception {
                String latestDbPath = bungieApi.latestManifestUrl().getResponse().getAsJsonObject("mobileWorldContentPaths").get("en").getAsString();
                String dbName = latestDbPath.substring(34);
                if (!hasDb(dbName)) {
                    saveManifest(bungieApi.zippedManifest(latestDbPath));
                }
                return SQLiteDatabase.openDatabase(mContext.getDatabasePath(dbName).getPath(),
                        null,
                        SQLiteDatabase.OPEN_READONLY);
            }
        });
    }

    @Override
    public List<ItemDefinition> getDefinitionsFor(List<ItemInstance> instanceList) {
        List<ItemDefinition> definitions = new ArrayList<>(instanceList.size());
        try {
            SQLiteDatabase definitionsDb = getDatabase();
            for (ItemInstance instance : instanceList) {
                Cursor resultSet = definitionsDb.rawQuery("SELECT json FROM DestinyInventoryItemDefinition WHERE id = " + Long.valueOf(instance.getItemHash().toString(), 10).intValue(),
                        null);
                if (resultSet.moveToFirst()) {
                    definitions.add(gson.fromJson(resultSet.getString(0), ItemDefinition.class));
                } else {
                    definitions.add(null);
                }
                resultSet.close();
            }
//            definitionsDb.close();
        } catch (SQLiteException exception) {
            throw new IllegalStateException(exception.getMessage(), exception);
        }
        return definitions;
    }

    @Override
    public List<ItemDefinition> getExoticDefinitions() {
        if (exotics == null) {
            exotics = new ArrayList<>();
            try {
                SQLiteDatabase definitionsDb = getDatabase();
                Cursor resultSet = definitionsDb.rawQuery("SELECT json FROM DestinyInventoryItemDefinition",
                        null);
                while (resultSet.moveToNext()) {
                    ItemDefinition tmp = gson.fromJson(resultSet.getString(0), ItemDefinition.class);
                    if (tmp.getTierType() == 6
                            && (tmp.getItemType() == 2 || tmp.getItemType() == 3)
                            && !tmp.getItemName().equals("Exotic Engram")
                            && !(tmp.getItemName().contains("###") || tmp.getItemName().isEmpty())) {
                        exotics.add(tmp);
                    }
                }
                resultSet.close();
//            definitionsDb.close();
            } catch (SQLiteException exception) {
                throw new IllegalStateException(exception.getMessage(), exception);
            }
        }
        return exotics;
    }

    private boolean hasDb(String databaseName) {
        //See http://stackoverflow.com/questions/3386667/query-if-android-database-exists
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(mContext.getDatabasePath(databaseName).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            //Could not open DB so it does not exist
            Log.i(TAG, "hasDb: false");
            return false;
        }
        //DB exists
        Log.i(TAG, "hasDb: " + databaseName);
        return true;
    }

    private void saveManifest(InputStream inputStream) {
        try {
            //Setting up the location for the file
            String location = mContext.getDatabasePath("manifest").getParent().concat("/");

            //Getting the file
            InputStream zippedFile = inputStream;

            //File writing setup
            int size;
            byte[] buffer = new byte[1024];

            //Making sure the location exists and is a directory
            File unzippedFile = new File(location);
            if (!unzippedFile.isDirectory()) {
                unzippedFile.mkdirs();
            }

            //Cleaning it, as to get rid of older DBs
            //FileUtils.cleanDirectory(unzippedFile);
            File[] flist;
            flist = unzippedFile.listFiles();
            if (flist != null && flist.length > 0) {
                for (File f : flist) {
                    f.delete();
                }
            }

            //Unzip magic
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(zippedFile));
            try {
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();
                    File unzipFile = new File(path);

                    if (ze.isDirectory()) {
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        //Check for and create parent directories if they don't exist
                        File parentDir = unzipFile.getParentFile();
                        if (null != parentDir) {
                            if (!parentDir.isDirectory()) {
                                parentDir.mkdirs();
                            }
                        }

                        //Unzip the file
                        FileOutputStream out = new FileOutputStream(unzipFile, false);
                        BufferedOutputStream fout = new BufferedOutputStream(out, 1024);
                        try {
                            while ((size = zin.read(buffer, 0, 1024)) != -1) {
                                fout.write(buffer, 0, size);
                            }

                            zin.closeEntry();
                        } finally {
                            fout.flush();
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
                Log.d(TAG, "Saved manifest DB to file at: " + location + unzippedFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

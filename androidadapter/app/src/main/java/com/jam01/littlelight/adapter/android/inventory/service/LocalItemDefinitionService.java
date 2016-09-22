package com.jam01.littlelight.adapter.android.inventory.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jam01.littlelight.adapter.android.common.RetrofitDestinyApiFacade;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by jam01 on 7/27/16.
 */
public class LocalItemDefinitionService {
    private static LocalItemDefinitionService INSTANCE;
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private String availableManifestDb;

    private LocalItemDefinitionService() {
    }

    public static synchronized LocalItemDefinitionService getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LocalItemDefinitionService();
        return INSTANCE;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void downloadLatestDefinitions() {
        String latestManifestLocation = RetrofitDestinyApiFacade.getInstance().latestManifestUrl();
        if (checkIfExistsLocally(latestManifestLocation.substring(34))) {
            return;
        }
        downloadDb(latestManifestLocation);
        availableManifestDb = latestManifestLocation.substring(34);
    }

    public String getDbPath() {
        return mContext.getDatabasePath(availableManifestDb).getPath();
    }

    public boolean checkIfExistsLocally(String database) {
        //See http://stackoverflow.com/questions/3386667/query-if-android-database-exists
        SQLiteDatabase checkDB;

        try {
            checkDB = SQLiteDatabase.openDatabase(mContext.getDatabasePath(database).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            //Could not open DB so it does not exist
            return false;
        }
        //DB exists
        return true;
    }

    private boolean downloadDb(String dbUrl) {
        try {
            //Setting up the location for the file
            String location = mContext.getDatabasePath("manifest").getParent().concat("/");

            //Getting the file
            InputStream zippedFile = RetrofitDestinyApiFacade.getInstance().manifestDb(dbUrl).byteStream();

            //File writing setup
            int size;
            byte[] buffer = new byte[1024];

            //Making sure the location exists and is a directory
            File unzippedFile = new File(location);
            if (!unzippedFile.isDirectory()) {
                unzippedFile.mkdirs();
            }

            //Cleaning it, as to get rid of older DBs
            FileUtils.cleanDirectory(unzippedFile);

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
                Log.d(TAG, "Saved manifest DB to file at: " + location);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

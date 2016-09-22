package com.jam01.littlelight.adapter.android.rxlegacy.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.bungie.netplatform.ItemDefinition;
import com.bungie.netplatform.ItemInstance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jam01.littlelight.adapter.android.rxlegacy.data.RetrofitNetworkClient;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jam01 on 5/14/16.
 */
public class ItemDefinitionRepository {
    private Context mContext;
    private Gson gson;
    private String TAG = getClass().getSimpleName();
    private Observable<String> latestManifestObservable;
    private static ItemDefinitionRepository mInstance;

    private ItemDefinitionRepository(Context context) {
        mContext = context;
        gson = new Gson();
    }

    //Singleton magic
    public static synchronized ItemDefinitionRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ItemDefinitionRepository(context);
        }
        return mInstance;
    }

    /**
     * @return an Observable that emits the url of the latest Item Definition Database
     */
    public Observable<String> getLatestDefinitionUrl() {
        if (latestManifestObservable == null) {
            latestManifestObservable = RetrofitNetworkClient.getInstance().getBungieApi().requestManifestUrl()
                    .map(new Func1<JsonObject, String>() {
                        @Override
                        public String call(JsonObject jsonObject) {
                            return jsonObject.getAsJsonObject("Response").getAsJsonObject("mobileWorldContentPaths").get("en").getAsString();
                        }
                    })
                    .replay(1)
                    .autoConnect();
        }
        return latestManifestObservable;
    }

    /**
     * @param database String that contains the name of the database to validate
     * @return an Observable that emits a String with the name of the DB if it exists locally
     */
    public Observable<String> validateLocalDb(final String database) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //See http://stackoverflow.com/questions/3386667/query-if-android-database-exists
                SQLiteDatabase checkDB = null;

                try {
                    checkDB = SQLiteDatabase.openDatabase(mContext.getDatabasePath(database).getPath(), null, SQLiteDatabase.OPEN_READONLY);
                    checkDB.close();
                } catch (SQLiteException e) {
                    //Could not open DB so it does not exist & Not printing StackTrace because error is expected
                    //e.printStackTrace();
                    Log.d(TAG, "SQLite does not exist");
                }

                if (checkDB != null) {
                    //DB exists
                    Log.d(TAG, "SQLite does exist");
                    subscriber.onNext(database);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * @return an Observable that emits the name of the latest database.
     * It checks if we have the DB locally and returns its name, if not if requests it for download
     */
    public Observable<String> getDbName() {
        return getLatestDefinitionUrl()
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String latestDbUrl) {
                        return Observable.concat(validateLocalDb(latestDbUrl.substring(34)),
                                downloadDb(latestDbUrl))
                                .take(1);
                    }
                });
    }

    /**
     * @param dbUrl String that contains the Url that hosts the Db to download
     * @return an Observable the emits a String containing the name of the database once it's done downloading and uzipping
     */
    private Observable<String> downloadDb(final String dbUrl) {
        return RetrofitNetworkClient.getInstance().getBungieApi().downloadManifestWithDynamicUrl(dbUrl)
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(final ResponseBody responseBody) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                try {
                                    //Setting up the location for the file
                                    String location = mContext.getDatabasePath("manifest").getParent().concat("/");

                                    //Getting the file
                                    InputStream zippedFile = responseBody.byteStream();

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
                                        subscriber.onNext(dbUrl.substring(34));
                                        subscriber.onCompleted();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    public Observable<ItemDefinition> getItemDefinition(final List<ItemInstance> list) {
        return getDbName()
                .flatMap(new Func1<String, Observable<ItemDefinition>>() {
                    @Override
                    public Observable<ItemDefinition> call(final String database) {
                        return Observable.create(new Observable.OnSubscribe<ItemDefinition>() {
                            @Override
                            public void call(Subscriber<? super ItemDefinition> subscriber) {
                                try {
                                    SQLiteDatabase checkDB = SQLiteDatabase.openDatabase(mContext.getDatabasePath(database).getPath(), null, SQLiteDatabase.OPEN_READONLY);
                                    for (ItemInstance instance : list) {
                                        Cursor resultSet = checkDB.rawQuery("SELECT json FROM DestinyInventoryItemDefinition WHERE id = " + Long.valueOf(instance.getItemHash().toString(), 10).intValue(), null);
                                        if (resultSet.moveToFirst()) {
                                            subscriber.onNext(gson.fromJson(resultSet.getString(0), ItemDefinition.class));
                                        } else {
                                            subscriber.onNext(null);
                                        }
                                        resultSet.close();
                                    }
                                    checkDB.close();
                                    Log.d(TAG, "call: calling onCompleted");
                                    subscriber.onCompleted();
                                } catch (SQLiteException exception) {
                                    subscriber.onError(exception);
                                }
                            }
                        });
                    }
                });
    }
}

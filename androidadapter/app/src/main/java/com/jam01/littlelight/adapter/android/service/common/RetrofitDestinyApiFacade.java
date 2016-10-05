package com.jam01.littlelight.adapter.android.service.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.api.EquipCommand;
import com.bungie.netplatform.destiny.api.TransferCommand;
import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.bungie.netplatform.destiny.representation.User;
import com.bungie.netplatform.destiny.representation.UserResponse;
import com.bungie.netplatform.destiny.representation.Vault;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by jam01 on 7/26/16.
 */
public class RetrofitDestinyApiFacade implements DestinyApi {
    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final RetrofitDestinyApi bungieApi;
    private final String TAG = this.getClass().getSimpleName();
    private final Context context;
    private Gson gson;
    private Future<SQLiteDatabase> database;

    public RetrofitDestinyApiFacade(Context mContext) {
        Log.d(TAG, "RetrofitDestinyApiFacade: instantiated!");
        this.context = mContext;
        gson = new Gson();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.bungie.net")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bungieApi = retrofit.create(RetrofitDestinyApi.class);

        database = Executors.newCachedThreadPool().submit(new Callable<SQLiteDatabase>() {
            @Override
            public SQLiteDatabase call() throws Exception {
                String latestDbPath = latestManifestUrl();
                String dbName = latestDbPath.substring(34);
                if (!hasDb(dbName)) {
                    addDbFrom(manifestDb(latestDbPath).byteStream());
                }
                Log.i(TAG, "call: " + dbName);
                return SQLiteDatabase.openDatabase(context.getDatabasePath(dbName).getPath(),
                        null,
                        SQLiteDatabase.OPEN_READONLY);
            }
        });
    }

    private boolean hasDb(String databaseName) {
        //See http://stackoverflow.com/questions/3386667/query-if-android-database-exists
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(databaseName).getPath(), null, SQLiteDatabase.OPEN_READONLY);
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

    private void addDbFrom(InputStream inputStream) {
        try {
            //Setting up the location for the file
            String location = context.getDatabasePath("manifest").getParent().concat("/");

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

    @Override
    public Vault getVault(int membershipType, String cookies, String xcsrf) {
        Vault vault;
        try {
            vault = bungieApi.requestVault(membershipType, cookies, xcsrf).execute().body().getResponse().getData();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return vault;
    }

    @Override
    public CharacterInventory getCharacterInventory(int membershipType, String membershipId, String characterId, String cookies, String xcsrf) {
        CharacterInventory characterInventory;
        try {
            BungieResponse<DataResponse<CharacterInventory>> response = bungieApi.requestCharacterInventory(membershipType, membershipId, characterId, cookies, xcsrf)
                    .execute()
                    .body();

            Log.i(TAG, "getCharacterInventory: " + response.getErrorStatus());
            characterInventory = response.getResponse().getData();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return characterInventory;
    }

    @Override
    public boolean equipItem(EquipCommand command, String cookies, String xcsrf) {
        try {
            BungieResponse<Integer> response = bungieApi.requestEquip(command, cookies, xcsrf).execute().body();
            if (response.getErrorCode() != 1) {
                throw new UnsupportedOperationException(response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean transferItem(TransferCommand command, String cookies, String xcsrf) {
        try {
            BungieResponse<Integer> response = bungieApi.requestTransfer(command, cookies, xcsrf).execute().body();//.getErrorCode() == 1;
            if (response.getErrorCode() != 1) {
                throw new UnsupportedOperationException(response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Account getAccount(int membershipType, String membershipId, String cookies, String xcsrf) {
        Account account = null;
        try {
            account = bungieApi.requestAccount(membershipType, membershipId, cookies, xcsrf).execute().body().getResponse().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public User getUser(String cookies, String xcsrf) {
        User user = null;
        try {
            user = bungieApi.requestUser(cookies, xcsrf).execute().body().getResponse().getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public JsonObject membershipIds(String cookies, String xcsrf) {
        JsonObject toReturn = null;
        try {
            toReturn = bungieApi.requestMembershiIds(cookies, xcsrf).execute().body().getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public String latestManifestUrl() {
        String dbUrl;
        try {
            dbUrl = bungieApi.requestManifestUrl().execute().body().getResponse().getAsJsonObject("mobileWorldContentPaths").get("en").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage(), e);
        }
        return dbUrl;
    }

    public ResponseBody manifestDb(String manifestUrl) {
        ResponseBody bodyStream;
        try {
            bodyStream = bungieApi.downloadManifestWithDynamicUrl(manifestUrl).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return bodyStream;
    }

    @Override
    public List<ItemDefinition> getDefinitionsFor(List<ItemInstance> instanceList) {
        List<ItemDefinition> definitions = new ArrayList<>(instanceList.size());
        try {
            SQLiteDatabase definitionsDb = database.get();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return definitions;
    }

    public interface RetrofitDestinyApi {
        String apiKey = "someKey";

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/Destiny/{membershipType}/Account/{membershipId}/Character/{characterId}/Inventory")
        Call<BungieResponse<DataResponse<CharacterInventory>>> requestCharacterInventory(@Path("membershipType") int membershipType,
                                                                                         @Path("membershipId") String membershipId,
                                                                                         @Path("characterId") String characterId,
                                                                                         @Header("Cookie") String cookie,
                                                                                         @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/Destiny/{membershipType}/MyAccount/Vault")
        Call<BungieResponse<DataResponse<Vault>>> requestVault(@Path("membershipType") int membershipType,
                                                               @Header("Cookie") String cookie,
                                                               @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/User/GetMembershipIds")
        Call<BungieResponse<JsonObject>> requestMembershiIds(@Header("Cookie") String cookie,
                                                             @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @POST("/Platform/Destiny/EquipItem/")
        Call<BungieResponse<Integer>> requestEquip(@Body EquipCommand command,
                                                   @Header("Cookie") String cookie,
                                                   @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @POST("/Platform/Destiny/TransferItem/")
        Call<BungieResponse<Integer>> requestTransfer(@Body TransferCommand command,
                                                      @Header("Cookie") String cookie,
                                                      @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/Destiny/{membershipType}/Account/{membershipId}")
        Call<BungieResponse<DataResponse<Account>>> requestAccount(@Path("membershipType") int membershipType,
                                                                   @Path("membershipId") String membershipId,
                                                                   @Header("Cookie") String cookie,
                                                                   @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/User/GetBungieNetUser/")
        Call<BungieResponse<UserResponse>> requestUser(@Header("Cookie") String cookie,
                                                       @Header("X-CSRF") String xcsrf);

        @Headers("X-API-KEY: " + apiKey)
        @GET("/Platform/Destiny/Manifest")
        Call<BungieResponse<JsonObject>> requestManifestUrl();

        @GET
        @Streaming
        Call<ResponseBody> downloadManifestWithDynamicUrl(@Url String manifestUrl);
    }
}

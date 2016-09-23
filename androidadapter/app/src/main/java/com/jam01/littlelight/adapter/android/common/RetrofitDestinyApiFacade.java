package com.jam01.littlelight.adapter.android.common;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
import com.jam01.littlelight.adapter.android.inventory.service.LocalItemDefinitionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static RetrofitDestinyApiFacade mInstance;
    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final BungieInventoryApi bungieApi;
    private final String TAG = this.getClass().getSimpleName();
    private Gson gson;

    private RetrofitDestinyApiFacade() {
        gson = new Gson();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.bungie.net")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bungieApi = retrofit.create(BungieInventoryApi.class);
    }

    public static synchronized RetrofitDestinyApiFacade getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitDestinyApiFacade();
        }
        return mInstance;
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
            characterInventory = bungieApi.requestCharacterInventory(membershipType, membershipId, characterId, cookies, xcsrf).execute().body().getResponse().getData();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return characterInventory;
    }

    @Override
    public boolean equipItem(EquipCommand command, String cookies, String xcsrf) {
        boolean result = false;
        try {
            result = bungieApi.requestEquip(command).execute().body().getErrorCode() == 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean transferItem(TransferCommand command, String cookies, String xcsrf) {
        boolean result = false;
        try {
            result = bungieApi.requestTransfer(command).execute().body().getErrorCode() == 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
            dbUrl = bungieApi.requestManifestUrl().execute().body().getAsJsonObject("Response").getAsJsonObject("mobileWorldContentPaths").get("en").getAsString();
        } catch (IOException e) {
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
            SQLiteDatabase definitionsDb = SQLiteDatabase.openDatabase(LocalItemDefinitionService.getInstance().getDbPath(),
                    null,
                    SQLiteDatabase.OPEN_READONLY);
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
            definitionsDb.close();
        } catch (SQLiteException exception) {
            throw new IllegalStateException(exception.getMessage(), exception);
        }
        return definitions;
    }

    private interface BungieInventoryApi {
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
        @POST("/Platform/Destiny/EquipItem")
        Call<BungieResponse<Integer>> requestEquip(@Body EquipCommand command);

        @Headers("X-API-KEY: " + apiKey)
        @POST("/Platform/Destiny/TransferItem")
        Call<BungieResponse<Integer>> requestTransfer(@Body TransferCommand command);

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


        @GET("/Platform/Destiny/Manifest")
        Call<JsonObject> requestManifestUrl();

        @GET
        @Streaming
        Call<ResponseBody> downloadManifestWithDynamicUrl(@Url String manifestUrl);
    }
}

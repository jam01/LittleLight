package com.jam01.littlelight.adapter.android.service.common;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.api.EquipCommand;
import com.bungie.netplatform.destiny.api.TransferCommand;
import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.Advisors;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.bungie.netplatform.destiny.representation.UserResponse;
import com.bungie.netplatform.destiny.representation.Vault;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
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
    private Gson gson;

    public RetrofitDestinyApiFacade() {
        gson = new Gson();

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
//                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.bungie.net")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bungieApi = retrofit.create(RetrofitDestinyApi.class);
    }

    @Override
    public BungieResponse<DataResponse<Vault>> getVault(int membershipType, String cookies, String xcsrf) {
        try {
            return bungieApi.requestVault(membershipType, cookies, xcsrf).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<DataResponse<CharacterInventory>> getCharacterInventory(int membershipType, String membershipId, String characterId, String cookies, String xcsrf) {
        try {
            return bungieApi.requestCharacterInventory(membershipType, membershipId, characterId, cookies, xcsrf)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<Integer> equipItem(EquipCommand command, String cookies, String xcsrf) {
        try {
            return bungieApi.requestEquip(command, cookies, xcsrf).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<Integer> transferItem(TransferCommand command, String cookies, String xcsrf) {
        try {
            return bungieApi.requestTransfer(command, cookies, xcsrf).execute().body();//.getErrorCode() == 1;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<DataResponse<Account>> getAccount(int membershipType, String membershipId, String cookies, String xcsrf) {
        try {
            return bungieApi.requestAccount(membershipType, membershipId, cookies, xcsrf).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<UserResponse> getUser(String cookies, String xcsrf) {
        try {
            return bungieApi.requestUser(cookies, xcsrf).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<JsonObject> membershipIds(String cookies, String xcsrf) {
        try {
            return bungieApi.requestMembershiIds(cookies, xcsrf).execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<JsonObject> latestManifestUrl() {
        try {
            return bungieApi.requestManifestUrl().execute().body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public BungieResponse<DataResponse<Advisors>> getAdvisorsForCharacter(int membershipType, String membershipId, String characterId, String cookies, String xcsrf) {
        try {
            return bungieApi.requestCharacterAdvisors(membershipType, membershipId, characterId, cookies, xcsrf)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream zippedManifest(String manifestUrl) {
        try {
            return bungieApi.downloadManifestWithDynamicUrl(manifestUrl).execute().body().byteStream();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
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
        @GET("/Platform/Destiny/{membershipType}/Account/{membershipId}/Character/{characterId}/Advisors")
        Call<BungieResponse<DataResponse<Advisors>>> requestCharacterAdvisors(@Path("membershipType") int membershipType,
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

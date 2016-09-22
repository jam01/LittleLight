package com.jam01.littlelight.adapter.android.rxlegacy.data;

import android.util.Log;
import android.webkit.CookieManager;

import com.google.gson.JsonObject;
import com.bungie.netplatform.Account;
import com.bungie.netplatform.BungieResponse;
import com.bungie.netplatform.CharacterInventory;
import com.bungie.netplatform.Vault;
import com.jam01.littlelight.adapter.android.legacy.Helpers.Endpoints;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by jam01 on 4/10/16.
 * See:
 * http://stackoverflow.com/questions/11165852/java-singleton-and-synchronization
 */
public class RetrofitNetworkClient {
    private static RetrofitNetworkClient mInstance;
    private final String TAG = this.getClass().getSimpleName();
    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final BungieApi bungieApi;
    private boolean hasValidCookies;

    private RetrofitNetworkClient() {
        Log.d(TAG, "RetrofitNetworkClient: new instance");
        hasValidCookies = checkCookiesValidity();

        //TODO: maybe add this interceptor as an inner class to use same CookieManager
        AddCredentialsInterceptor interceptor = new AddCredentialsInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.bungie.net")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        bungieApi = retrofit.create(BungieApi.class);
    }

    //Singleton magic
    public static synchronized RetrofitNetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitNetworkClient();
        }
        return mInstance;
    }

    public BungieApi getBungieApi() {
        return bungieApi;
    }

    public boolean isHasValidCookies() {
        Log.d(TAG, "isHasValidCookies: cookies are valid > " + hasValidCookies);
        return hasValidCookies;
    }

    private boolean checkCookiesValidity() {
        List<String> cookieValues = new ArrayList<>();

        CookieManager cookieManager = android.webkit.CookieManager.getInstance();

        String cookies = cookieManager.getCookie(Endpoints.BASE_URL);

        if (cookies == null || cookies.isEmpty()) {
            return false;
        }

        //TODO: Make these cookie values constants
        String[] temp = cookies.split(" ");
        for (String ar1 : temp) {
            if (ar1.contains("bungled=")) {
                cookieValues.add(ar1);
                Log.d("bungled=", ar1);
            } else if (ar1.contains("bungleatk=")) {
                cookieValues.add(ar1);
                Log.d("bungleatk=", ar1);
            }
        }

        return cookieValues.size() == 2;
    }

    public void updateCookiesValidity() {
        hasValidCookies = checkCookiesValidity();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    //TODO: Add check for internet connectivity before all calls to these methods
    //TODO: Add compose(Transformation) for all calls to these methods
    public interface BungieApi {
        @GET("/platform/User/GetMembershipIds")
        Call<JsonObject> requestMembershipId();

        @GET("/Platform/Destiny/{membershipType}/Account/{membershipId}")
        Observable<BungieResponse<Account>> requestAccount(@Path("membershipType") String membershipType,
                                                           @Path("membershipId") String membershipId);

        @GET("/Platform/Destiny/Manifest")
        Observable<JsonObject> requestManifestUrl();

        @GET
        @Streaming
        Observable<ResponseBody> downloadManifestWithDynamicUrl(@Url String manifestUrl);

        @GET("/Platform/Destiny/{membershipType}/Account/{membershipId}/Character/{characterId}/Inventory")
        Observable<BungieResponse<CharacterInventory>> requestCharacterInventory(@Path("membershipType") String membershipType,
                                                                                 @Path("membershipId") String membershipId,
                                                                                 @Path("characterId") String characterId);


        @GET("/Platform/Destiny/{membershipType}/MyAccount/Vault")
        Observable<BungieResponse<Vault>> requestVault(@Path("membershipType") String membershipType);


        @GET("/Platform/Destiny/EquipItem")
        Observable<BungieResponse<Vault>> requestEquip();


        @GET("/Platform/Destiny/TransferItem")
        Observable<BungieResponse<Vault>> requestTransfer();
    }
}

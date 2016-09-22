package com.jam01.littlelight.adapter.android.rxlegacy.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jam01.littlelight.adapter.android.rxlegacy.data.RetrofitNetworkClient;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jam01 on 4/13/16.
 */
public class CredentialsRepository {
    private SharedPreferences sharedPreferences;
    private String TAG = getClass().getSimpleName();
    private static CredentialsRepository mInstance;

    private CredentialsRepository(Context context) {
        Log.d(TAG, "CredentialsRepository: new instance");
        sharedPreferences = context.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
    }

    //Singleton magic
    public static synchronized CredentialsRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CredentialsRepository(context);
        }
        return mInstance;
    }

    public boolean exist() {
        return sharedPreferences.contains("SignedIn") &&
                RetrofitNetworkClient.getInstance().isHasValidCookies();
    }

    public Observable<String> validateByRetrievingMemberhipId(final int membershipType) {
        //TODO: Transform this to use generic Response
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Response<JsonObject> response = RetrofitNetworkClient.getInstance().getBungieApi().requestMembershipId().execute();
                    JsonObject ids = response.body().get("Response").getAsJsonObject();

                    for (Map.Entry<String, JsonElement> entry : ids.entrySet()) {
                        if (Integer.valueOf(entry.getValue().toString()) == membershipType) {
                            RetrofitNetworkClient.getInstance().updateCookiesValidity();
                            subscriber.onNext(entry.getKey());
                            subscriber.onCompleted();
                            return;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                    subscriber.onError(exception);
                }
            }
        });
    }

    public String getMembershipId() {
        return sharedPreferences.getString("membershipId", null);
    }

    public int getMembershipType() {
        return sharedPreferences.getInt("membershipType", -1);
    }

    public void save(final int membershipType, String membershipId) {
        sharedPreferences.edit().putString("membershipId", membershipId)
                .putInt("membershipType", membershipType)
                .putBoolean("SignedIn", true).apply();
    }

    public void delete() {
        sharedPreferences.edit().clear().apply();
    }

}

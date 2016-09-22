package com.jam01.littlelight.adapter.android.rxlegacy.data;

import android.webkit.CookieManager;

import com.jam01.littlelight.adapter.android.legacy.Helpers.Endpoints;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jam01 on 4/13/16.
 */
public class AddCredentialsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(Endpoints.BASE_URL);
        String cookieHeader = "";

        String[] temp = cookies.split(" ");
        for (String cookie : temp) {
            if (cookie.contains("bungled=")) {
                cookieHeader += cookie;
                builder.addHeader("x-csrf", cookie.substring(8, 27));
            } else if (cookie.contains("bungleatk=")){
                cookieHeader += cookie;
            }
        }
        builder.addHeader("Cookie", cookieHeader);
        builder.addHeader("X-API-KEY", "someKey");

        return chain.proceed(builder.build());
    }
}
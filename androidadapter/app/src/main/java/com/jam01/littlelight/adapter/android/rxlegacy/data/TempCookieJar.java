package com.jam01.littlelight.adapter.android.rxlegacy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by jam01 on 4/13/16.
 */
public class TempCookieJar implements okhttp3.CookieJar {
    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    public void addToStore(HttpUrl url, Cookie cookie) {
        List<Cookie> entry = cookieStore.get(url);
        if (entry != null){
            entry = new ArrayList<>(entry);
            entry.add(cookie);
            cookieStore.put(url, entry);
        }
        else
            cookieStore.put(url, Collections.singletonList(cookie));
    }
}


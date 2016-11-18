package com.jam01.littlelight.adapter.android.presentation.user;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bungie.netplatform.destiny.representation.Endpoints;

public class SignInFragment extends DialogFragment {
    private final static String URL = "url";
    private final String TAG = this.getClass().getSimpleName();
    private WebView webView;
    private String url;
    private UserPresenter.AccountCredentialsCallback callback;

    public static SignInFragment newInstance(String url) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.setWebViewClient(new WebViewClient() {
            //TODO: Figure out if both of these are necessary. I believe it might have to do with Android versions
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals(Endpoints.BASE_URL)) {
                    callback.onResult(collectCookies());
                    callback = null;
                    dismiss();
                } else
                    super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(Endpoints.BASE_URL)) {
                    callback.onResult(collectCookies());
                    callback = null;
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        return webView;
    }

    @Override
    public void onStart() {
        super.onStart();
        webView.loadUrl(url);
    }

    @SuppressWarnings("deprecation")
    private String[] collectCookies() {
        String cookies = CookieManager.getInstance().getCookie(Endpoints.BASE_URL);

        if (cookies == null || cookies.isEmpty()) {
            return null;
        }
        //See: http://stackoverflow.com/questions/28998241/how-to-clear-cookies-and-cache-of-webview-on-android-when-not-in-webview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(getActivity().getApplicationContext());
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }

        return cookies.split(" ");
    }

    public void setCallback(UserPresenter.AccountCredentialsCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroy() {
        if (callback != null)
            callback.onDismissed();
        super.onDestroy();
    }
}

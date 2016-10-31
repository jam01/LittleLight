package com.jam01.littlelight.adapter.android.presentation.user;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.R;

public class SignInActivity extends AppCompatActivity {
    Intent resultIntent = new Intent();
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        webView = (WebView) findViewById(R.id.webView);

        Intent callingIntent = getIntent();
        if (callingIntent.hasExtra("membershipType")) {
            int membershipType = callingIntent.getIntExtra("membershipType", -1);
            resultIntent.putExtra("membershipType", membershipType)
                    .putExtra("membershipId", callingIntent.getStringExtra("membershipId"));
            loadWebView(membershipType == 1 ? Endpoints.XBOX_AUTH_URL : Endpoints.PSN_AUTH_URL);
        } else
            showLoginDialog();
    }

    public void showLoginDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Little Light")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Where should I look for you, Guardian?")
                .setPositiveButton("PSN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultIntent.putExtra("membershipType", 2);
                        loadWebView(Endpoints.PSN_AUTH_URL);
                    }
                })
                .setNegativeButton("Xbox Live", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultIntent.putExtra("membershipType", 1);
                        loadWebView(Endpoints.XBOX_AUTH_URL);
                    }
                })
                .create()
                .show();
    }

    @SuppressWarnings("deprecation")
    public void loadWebView(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.setWebViewClient(new WebViewClient() {
            //TODO: Figure out if both of these are necessary. I believe it might have to do with Android versions
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals(Endpoints.BASE_URL)) {
                    resultIntent.putExtra("cookies", collectCookies());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else
                    super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(Endpoints.BASE_URL)) {
                    resultIntent.putExtra("cookies", collectCookies());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }

    @SuppressWarnings("deprecation")
    private String[] collectCookies() {
        String cookies = android.webkit.CookieManager.getInstance().getCookie(Endpoints.BASE_URL);

        if (cookies == null || cookies.isEmpty()) {
            return null;
        }
        //See: http://stackoverflow.com/questions/28998241/how-to-clear-cookies-and-cache-of-webview-on-android-when-not-in-webview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(getApplicationContext());
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }

        return cookies.split(" ");
    }
}

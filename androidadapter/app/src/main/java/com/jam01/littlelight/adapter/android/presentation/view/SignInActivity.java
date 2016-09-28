package com.jam01.littlelight.adapter.android.presentation.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.SignInPresenter;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;

import javax.inject.Inject;

public class SignInActivity extends AppCompatActivity implements SignInPresenter.SignInView {
    @Inject
    SignInPresenter presenter;
    private ProgressDialog progressDialog;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        webView = (WebView) findViewById(R.id.webView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Little Light");
        progressDialog.setMessage("Logging In");

        if (presenter == null) {
            presenter = ((LittleLight) getApplication()).getComponent().provideSignInPresenter();
        }
//            presenter = new SignInPresenter(new DestinyAccountImportService(
//                    new ACLAccountService(RetrofitDestinyApiFacade.getInstance()),
//                    new SingletonDiskUser(this)));
        presenter.bindView(this);
    }

    @Override
    public void showLoading(boolean show) {
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    @Override
    public void showLoginDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Little Light")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Where should I look for you, Guardian?")
                .setPositiveButton("PSN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onMembershipTypeSelected(2);
                    }
                })
                .setNegativeButton("Xbox Live", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onMembershipTypeSelected(1);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void loadWebView(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.setWebViewClient(new WebViewClient() {
            //TODO: Figure out if both of these are necessary. I believe it might have to do with Android versions
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals(Endpoints.BASE_URL)) {
                    presenter.onBungieUrlIntercepted(collect());
                } else
                    super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(Endpoints.BASE_URL)) {
                    presenter.onBungieUrlIntercepted(collect());
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }

    public void navigateToHome() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    private AccountCredentials collect() {
        String bungled = null, bungleatk = null;

        String cookies = android.webkit.CookieManager.getInstance().getCookie(Endpoints.BASE_URL);

        if (cookies == null || cookies.isEmpty()) {
            return null;
        }

        for (String ar1 : cookies.split(" ")) {
            if (ar1.contains("bungled=")) {
                bungled = ar1.substring(8);
                Log.d("bungled=", ar1);
            } else if (ar1.contains("bungleatk=")) {
                bungleatk = ar1.substring(10);
                Log.d("bungleatk=", ar1);
            }
        }

        return new AccountCredentials(bungled, bungleatk);
    }
}

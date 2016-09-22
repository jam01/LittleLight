//package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;
//
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.jam01.littlelight.R;
//import com.jam01.littlelight.adapter.android.legacy.Helpers.Endpoints;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter.SignInPresenter;
//
///**
// * Created by jam01 on 4/10/16.
// */
//public class SignInActivity extends AppCompatActivity implements SignInPresenter.SignInView {
//    private SignInPresenter presenter;
//    private ProgressDialog progressDialog;
//    private WebView webView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_sign_in);
//
//        webView = (WebView) findViewById(R.id.webView);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Little Light");
//        progressDialog.setMessage("Logging In");
//
//        if (presenter == null)
//            presenter = new SignInPresenter();
//        presenter.bindView(this);
//    }
//
//    @Override
//    public void showLoading(boolean show) {
//        if (show)
//            progressDialog.show();
//        else
//            progressDialog.dismiss();
//    }
//
//    @Override
//    public void showLoginDialog() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        dialogBuilder.setTitle("Little Light")
//                .setIcon(R.mipmap.ic_launcher)
//                .setMessage("Where should I look for you, Guardian?")
//                .setPositiveButton("PSN", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        presenter.onMembershipTypeSelected(2);
//                    }
//                })
//                .setNegativeButton("Xbox Live", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        presenter.onMembershipTypeSelected(1);
//                    }
//                })
//                .create()
//                .show();
//    }
//
//    @Override
//    public void loadWebView(String url) {
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSavePassword(false);
//        webView.setWebViewClient(new WebViewClient(){
//            //TODO: Figure out if both of these are necessary. I believe it might have to do with Android versions
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                if (url.equals(Endpoints.BASE_URL)) {
//                    presenter.onBungieUrlIntercepted();
//                } else
//                    super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.equals(Endpoints.BASE_URL)) {
//                    presenter.onBungieUrlIntercepted();
//                    return true;
//                }
//                return false;
//            }
//        });
//        webView.loadUrl(url);
//    }
//
//    public void navigateToHome() {
////        startActivity(new Intent(this, LegendActivity.class));
//        startActivity(new Intent(this, InventoryActivity.class));
//        finish();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        presenter.unbindView();
//    }
//}

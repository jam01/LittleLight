package com.jam01.littlelight.adapter.android.presentation.presenter;

import android.os.AsyncTask;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.application.DestinyAccountImportService;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;

import javax.inject.Inject;

/**
 * Created by jam01 on 9/22/16.
 */
public class SignInPresenter {
    private final String TAG = getClass().getSimpleName();
    private SignInView view;
    private DestinyAccountImportService service;
    private int membershipType;

    @Inject
    public SignInPresenter(DestinyAccountImportService service) {
        this.service = service;
    }

    public void bindView(SignInView view) {
        this.view = view;
        view.showLoginDialog();
    }

    public void unbindView() {
        view = null;
    }

    public void onMembershipTypeSelected(int membershipType) {
        this.membershipType = membershipType;
        view.loadWebView(membershipType == 1 ? Endpoints.XBOX_AUTH_URL : Endpoints.PSN_AUTH_URL);
    }

    public void onBungieUrlIntercepted(final AccountCredentials credentials) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                service.loadAccountFrom(membershipType, credentials);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showLoading(true);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                view.showLoading(false);
                view.navigateToHome();
            }
        }.execute();

//        Callable<Void> task = new Callable<Void>() {
//            @Override
//            public Void call() throws Exception {
//                view.navigateToHome();
//                return null;
//            }
//        };
//
//        try {
//            Executors.newSingleThreadExecutor().submit(task);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        view.navigateToHome();
    }

    public interface SignInView {
        void showLoading(boolean show);

        void showLoginDialog();

        void loadWebView(String url);

        void navigateToHome();
    }
}

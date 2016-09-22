//package com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter;
//
//import android.app.Activity;
//
//import com.jam01.littlelight.adapter.android.rxlegacy.domain.service.SignInService;
//import com.jam01.littlelight.adapter.android.legacy.Helpers.Endpoints;
//
//import rx.Subscription;
//import rx.subscriptions.Subscriptions;
//
///**
// * Created by jam01 on 4/10/16.
// */
//public class SignInPresenter implements SignInService.SignInServiceListener {
//    private final String TAG = getClass().getSimpleName();
//    private SignInView view;
//    private SignInService service;
//    private Subscription subscription = Subscriptions.empty();
//
//    public SignInPresenter() {
//    }
//
//    public void bindView(SignInView view){
//        this.view = view;
//        this.service = new SignInService(((Activity) view).getApplicationContext(), this);
//
//        view.showLoading(true);
//        service.validateCredentials();
//    }
//
//    public void unbindView(){
//        view = null;
//        if (!subscription.isUnsubscribed())
//            subscription.unsubscribe();
//    }
//
//    public void onMembershipTypeSelected(int membershipType) {
//        service.setMembershipType(membershipType);
//        view.loadWebView(membershipType == 1 ? Endpoints.XBOX_AUTH_URL : Endpoints.PSN_AUTH_URL);
//    }
//
//    public void onBungieUrlIntercepted() {
//        view.showLoading(true);
//        service.signIn();
//    }
//
//    @Override
//    public void onHasCredentials(boolean hasThem) {
//        if (hasThem) {
//            view.showLoading(false);
//            view.navigateToHome();
//
//        } else {
//            view.showLoading(false);
//            view.showLoginDialog();
//        }
//    }
//
//    public interface SignInView {
//        void showLoading(boolean show);
//        void showLoginDialog();
//        void loadWebView(String url);
//        void navigateToHome();
//    }
//}

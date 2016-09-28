package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 9/22/16.
 */
public class SignInPresenter {
    private final String TAG = getClass().getSimpleName();
    private SignInView view;
    private UserService service;
    private int membershipType;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public SignInPresenter(UserService service) {
        this.service = service;
    }

    public void bindView(SignInView view) {
        this.view = view;
        view.showLoginDialog();
    }

    public void unbindView() {
        view = null;
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void onMembershipTypeSelected(int membershipType) {
        this.membershipType = membershipType;
        view.loadWebView(membershipType == 1 ? Endpoints.XBOX_AUTH_URL : Endpoints.PSN_AUTH_URL);
    }

    public void onBungieUrlIntercepted(final AccountCredentials credentials) {
        subscription = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.registerFromCredentials(membershipType, credentials);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        view.navigateToHome();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoginDialog();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                    }
                });
    }

    public interface SignInView {
        void showLoading(boolean show);

        void showLoginDialog();

        void loadWebView(String url);

        void navigateToHome();
    }
}

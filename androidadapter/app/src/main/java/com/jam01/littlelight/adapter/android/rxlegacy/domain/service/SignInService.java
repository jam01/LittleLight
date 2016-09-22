package com.jam01.littlelight.adapter.android.rxlegacy.domain.service;

import android.content.Context;

import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.CredentialsRepository;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 4/10/16.
 */
public class SignInService {
    private CredentialsRepository repository;
    private int membershipType;
    private SignInServiceListener listener;
    private String TAG = getClass().getSimpleName();
    private Subscription subscription = Subscriptions.empty();

    public SignInService(Context applicationContext, SignInServiceListener listener) {
        repository = CredentialsRepository.getInstance(applicationContext);
        this.listener = listener;
    }

    public void validateCredentials() {
        listener.onHasCredentials(repository.exist());
    }

    public void signIn() {
        subscription = repository.validateByRetrievingMemberhipId(membershipType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        listener.onHasCredentials(repository.exist());
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onHasCredentials(false);
                    }

                    @Override
                    public void onNext(String membershipId) {
                        repository.save(membershipType, membershipId);
                    }
                });
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void setMembershipType(int membershipType) {
        this.membershipType = membershipType;
    }

    public interface SignInServiceListener {
        void onHasCredentials(boolean bool);
    }
}

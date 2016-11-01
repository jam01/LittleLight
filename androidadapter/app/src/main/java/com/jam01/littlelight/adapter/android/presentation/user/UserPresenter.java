package com.jam01.littlelight.adapter.android.presentation.user;

import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountUpdated;
import com.jam01.littlelight.domain.identityaccess.User;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jam01 on 9/23/16.
 */
public class UserPresenter {
    private final String TAG = this.getClass().getSimpleName();
    private MainView view;
    private UserService service;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private OnErrorAction errorAction = new OnErrorAction();

    @Inject
    public UserPresenter(final UserService service) {
        this.service = service;
    }

    public void bindView(MainView mainView) {
        view = mainView;
    }

    public void unbindView() {
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void onStart() {
        if (subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        subscriptions.add(service.subscribeToUserEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DomainEvent>() {
                               @Override
                               public void call(DomainEvent domainEvent) {
                                   if (domainEvent instanceof AccountUpdated) {
                                       view.updateAccount(((AccountUpdated) domainEvent).getAccountUpdated());
                                   } else if (domainEvent instanceof AccountCredentialsExpired) {
                                       view.showWebSignInForUpdatingCredentials(((AccountCredentialsExpired) domainEvent).getExpiredAccount().withId());
                                   }
                               }
                           }
                        , errorAction
                ));

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.synchronizeUserAccounts();
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                }, errorAction));

        view.setUser(service.getUser());
        if (service.userAccounts().isEmpty()) {
            view.showWebSignIn();
        }
    }

    public void onAddAccount() {
        view.showWebSignIn();
    }

    public void onRemoveAccount(Account account) {
        service.unregister(account.withId());
        view.removeAccount(account);
    }

    public void onWebSignInCompleted(final int membershipType, final String[] cookies) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                subscriber.onNext(service.registerFromCredentials(membershipType, cookies));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Account>() {
                    @Override
                    public void call(Account aVoid) {
                        view.addAccount(aVoid);
                        view.displayAccount(aVoid);
                        view.showLoading(false);
                    }
                }, errorAction));

        view.showLoading(true);
    }

    public void onWebCredentialsUpdated(final AccountId accountId, final String[] cookies) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                service.updateCredentials(accountId, cookies);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Account>() {
                    @Override
                    public void call(Account aVoid) {
                        view.displayAccount(aVoid);
                        view.showLoading(false);
                    }
                }, errorAction));
        view.showLoading(true);
    }

    public interface MainView {
        void showWebSignInForUpdatingCredentials(AccountId accountId);

        void showWebSignIn();

        void setUser(User user);

        void showError(String localizedMessage);

        void updateAccount(Account accountUpdated);

        void removeAccount(Account account);

        void addAccount(Account accountToAdd);

        void displayAccount(Account account);

        void showLoading(boolean bool);
    }

    private class OnErrorAction implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            throwable.printStackTrace();
            view.showError(throwable.getLocalizedMessage());
            view.showLoading(false);
        }
    }
}

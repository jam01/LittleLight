package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.identityaccess.Account;
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
    private MainView view;
    private UserService service;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject
    public UserPresenter(UserService service) {
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

        if (service.userAccounts().isEmpty()) {
            view.loadSignInView();
        } else {
            view.setUser(service.getUser());
            subscriptions.add(service.subscribeToUserEvents()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<DomainEvent>() {
                                   @Override
                                   public void call(DomainEvent domainEvent) {
                                       if (domainEvent instanceof AccountUpdated) {
                                           view.updateAccount(((AccountUpdated) domainEvent).getAccountUpdated());
                                       }
                                   }
                               }

                            , new OnErrorAction()
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
                    }, new OnErrorAction()));
        }
    }

    public void onAddAccount() {
        view.loadSignInView();
    }

    public void onRemoveAccount(Account account) {
        service.unregister(account.withId());
        view.removeAccount(account);
    }

    public interface MainView {
        void loadSignInView();

        void setUser(User user);

        void updateAccount(Account accountUpdated);

        void removeAccount(Account account);
    }

    private class OnErrorAction implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

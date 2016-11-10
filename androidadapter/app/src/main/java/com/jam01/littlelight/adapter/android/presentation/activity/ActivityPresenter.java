package com.jam01.littlelight.adapter.android.presentation.activity;

import com.jam01.littlelight.application.ActivityService;
import com.jam01.littlelight.domain.activity.Account;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jam01 on 11/9/16.
 */
public class ActivityPresenter {
    private final String TAG = getClass().getSimpleName();
    private ActivityService service;
    private ActivityView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private OnCompletedAction completedAction = new OnCompletedAction();
    private OnErrorAction errorAction = new OnErrorAction();

    @Inject
    public ActivityPresenter(ActivityService service) {
        this.service = service;
    }

    public void refresh(final AccountId anAccountId) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                subscriber.onNext(service.ofAccount(anAccountId));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnActivityAction(), errorAction, new Action0() {
                    @Override
                    public void call() {
                        view.showLoading(false);
                    }
                }));
    }

    public void bindView(ActivityView inventoryView) {
        this.view = inventoryView;
    }

    public void unbindView() {
        view.showLoading(false);
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                subscriber.onNext(service.ofAccount(anAccountId));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnActivityAction(), errorAction, new Action0() {
                    @Override
                    public void call() {
                        view.showLoading(false);
                    }
                }));

    }

    public interface ActivityView {

        void renderActivity(Account account);

        void showLoading(boolean show);

        void showError(String localizedMessage);
    }

    private class OnActivityAction implements Action1<Account> {

        @Override
        public void call(Account account) {
            view.renderActivity(account);
        }
    }

    private class OnCompletedAction implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            view.showLoading(false);
        }
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

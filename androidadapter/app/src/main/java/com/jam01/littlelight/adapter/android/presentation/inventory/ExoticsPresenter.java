package com.jam01.littlelight.adapter.android.presentation.inventory;

import com.jam01.littlelight.adapter.common.presentation.ExoticsDPO;
import com.jam01.littlelight.application.InventoryService;
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
 * Created by jam01 on 11/8/16.
 */
public class ExoticsPresenter {
    private final String TAG = getClass().getSimpleName();
    private InventoryService service;
    private ExoticsView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private OnCompletedAction completedAction = new OnCompletedAction();
    private OnErrorAction errorAction = new OnErrorAction();

    @Inject
    public ExoticsPresenter(InventoryService inventoryService) {
        this.service = inventoryService;
    }

    public void bindView(ExoticsView exoticsView) {
        this.view = exoticsView;
    }


    public void unbindView() {
        view.showLoading(false);
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void onStart(final AccountId accountId) {
        view.showLoading(true);
        if (subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        // TODO: 11/8/16 Make this a zip rx
        subscriptions.add(Observable.create(new Observable.OnSubscribe<ExoticsDPO>() {
            @Override
            public void call(Subscriber<? super ExoticsDPO> subscriber) {
                subscriber.onNext(new ExoticsDPO(service.exoticsOf(accountId), service.exotics()));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnExoticsAction(), errorAction, new Action0() {
                    @Override
                    public void call() {
                        view.showLoading(false);
                    }
                }));
    }

    public void refresh(AccountId accountId) {

    }

    public interface ExoticsView {

        void renderExotics(ExoticsDPO exoticsList);

        void showLoading(boolean show);

        void showError(String message);
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

    private class OnExoticsAction implements Action1<ExoticsDPO> {
        @Override
        public void call(ExoticsDPO items) {
            view.renderExotics(items);
        }
    }
}

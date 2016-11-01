package com.jam01.littlelight.adapter.android.presentation.legend;

import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendUpdated;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jam01 on 9/5/16.
 */
public class LegendPresenter {
    private final String TAG = getClass().getSimpleName();
    private LegendService service;
    private LegendView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private OnCompletedAction completedAction = new OnCompletedAction();
    private OnErrorAction errorAction = new OnErrorAction();

    @Inject
    public LegendPresenter(LegendService legendService) {
        this.service = legendService;
    }

    public void bindView(LegendView inventoryView) {
        this.view = inventoryView;
    }


    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        subscriptions.add(service.subscribeToInventoryEvents(anAccountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DomainEvent>() {
                               @Override
                               public void call(DomainEvent domainEvent) {
                                   if (domainEvent instanceof LegendUpdated) {
                                       view.renderLegend(((LegendUpdated) domainEvent).getLegendUpdated());
                                   }
                               }
                           }, errorAction
                ));

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Legend>() {
            @Override
            public void call(Subscriber<? super Legend> subscriber) {
                subscriber.onNext(service.ofAccount(anAccountId));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnLegendAction(), errorAction, new Action0() {
                    @Override
                    public void call() {
                        view.showLoading(false);
                    }
                }));

    }

    public void unbindView() {
        view.showLoading(false);
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void refresh(final AccountId anAccountId) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.synchronizeLegendOf(anAccountId);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completedAction, errorAction));
    }

    public interface LegendView {
        void renderLegend(Legend legend);

        void showLoading(boolean show);

        void showError(String localizedMessage);
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

    private class OnLegendAction implements Action1<Legend> {
        @Override
        public void call(Legend legend) {
            view.renderLegend(legend);
        }
    }
}


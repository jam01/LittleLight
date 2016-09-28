package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jam01 on 9/5/16.
 */
public class InventoryPresenter {
    private final String TAG = getClass().getSimpleName();
    private InventoryService service;
    private InventoryView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject
    public InventoryPresenter(InventoryService inventoryService) {
        this.service = inventoryService;
    }

    public void bindView(InventoryView inventoryView, final AccountId anAccountId) {
        this.view = inventoryView;
        view.showLoading(true);

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Inventory>() {
            @Override
            public void call(Subscriber<? super Inventory> subscriber) {
                subscriber.onNext(service.ofAccount(anAccountId));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new InventorySubscriber()));
    }

    public void unbindView() {
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void sendItems(final List<Item> toTransfer, final String toItemBagId) {
        view.showLoading(true);

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (Item instance : toTransfer) {
                    service.transferItem(instance.getItemInstanceId(), toItemBagId);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TransferSubscriber()));
    }

    public void refresh(final AccountId anAccountId) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.synchronizeInventoryOf(anAccountId);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        view.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getLocalizedMessage());
                        view.setRefreshing(false);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                    }
                }));
    }

    public interface InventoryView {
        void renderInventory(Inventory inventory);

        void renderEmblem(String url);

        void showLoading(boolean show);

        void showError(String localizedMessage);

        void setRefreshing(boolean bool);
    }

    private class InventorySubscriber extends Subscriber<Inventory> {
        @Override
        public void onCompleted() {
            view.showLoading(false);
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getLocalizedMessage());
        }

        @Override
        public void onNext(Inventory inventory) {
            view.renderInventory(inventory);
        }
    }

    private class TransferSubscriber extends Subscriber<Void> {
        @Override
        public void onCompleted() {
            view.showLoading(false);
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getLocalizedMessage());
            view.showLoading(false);
        }

        @Override
        public void onNext(Void aVoid) {
        }
    }
}

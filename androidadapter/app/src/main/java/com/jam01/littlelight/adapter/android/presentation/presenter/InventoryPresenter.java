package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 9/5/16.
 */
public class InventoryPresenter {
    private final String TAG = getClass().getSimpleName();
    private InventoryService service;
    private InventoryView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public InventoryPresenter(InventoryService inventoryService) {
        this.service = inventoryService;
    }

    public void bindView(InventoryView inventoryView, final AccountId anAccountId) {
        this.view = inventoryView;
        view.showLoading(true);
        subscription = Observable.just(service.ofAccount(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new InventorySubscriber());
    }

    public void unbindView() {
        view = null;
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void sendItems(final List<Item> toTransfer, final String toItemBagId) {
        view.showLoading(true);

        subscription = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (Item instance : toTransfer) {
                    service.transferItem(instance.getItemInstanceId(), toItemBagId);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TransferSubscriber());
    }

    public void refresh(AccountId anAccountId) {
        service.synchronizeInventoryOf(anAccountId);
    }

    public interface InventoryView {
        void renderInventory(Inventory inventory);

        void renderEmblem(String url);

        void showLoading(boolean show);

        void showError(String localizedMessage);
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
        }

        @Override
        public void onNext(Void aVoid) {
        }
    }
}

package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.ItemService;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 9/24/16.
 */
public class ItemBagPresenter {
    private final String TAG = getClass().getSimpleName();
    private ItemService service;
    private ItemBagView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public ItemBagPresenter(ItemService service) {
        this.service = service;
    }

    public void bindView(ItemBagView view) {
        this.view = view;
    }

    public void unbindView() {
        view = null;
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void equipItem(final Item item, final String characterId) {
        subscription = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.equipItem(item.getItemInstanceId(), characterId);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EquipItemSubscriber());
    }

    public interface ItemBagView {
        void showLoading(boolean show);

        void replaceItems(ItemBag itemBag);

        void clearItems();

        void updateItem(Item anItem);

        void removeItem(Item anItem);

        void showError(String localizedMessage);
    }

    private class EquipItemSubscriber extends Subscriber<Void> {
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

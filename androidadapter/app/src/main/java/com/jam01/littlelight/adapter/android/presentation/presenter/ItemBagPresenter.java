package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.ItemService;
import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.ItemBagUpdated;
import com.jam01.littlelight.domain.inventory.ItemEquipped;
import com.jam01.littlelight.domain.inventory.ItemTransferred;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jam01 on 9/24/16.
 */
public class ItemBagPresenter {
    private final String TAG = getClass().getSimpleName();
    private ItemService service;
    private ItemBagView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject
    public ItemBagPresenter(ItemService service) {
        this.service = service;
    }

    public void bindView(final ItemBagView view) {
        this.view = view;
        subscriptions.add(service.getItemBagEvents(view.itemBagRendering())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ItemBagSubscriber()));
    }

    public void unbindView() {
        view = null;
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void equipItem(final Item item, final String characterId) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.equipItem(item.getItemInstanceId(), characterId);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EquipItemSubscriber()));
    }

    public interface ItemBagView {
        void showLoading(boolean show);

        String itemBagRendering();

        void replaceItems(ItemBag itemBag);

        void clearItems();

        void updateItem(Item anItem);

        void removeItem(Item anItem);

        void showError(String localizedMessage);

        void addItem(Item itemTransferred);
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

    private class ItemBagSubscriber extends Subscriber<DomainEvent> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getLocalizedMessage());
        }

        @Override
        public void onNext(DomainEvent domainEvent) {
            if (domainEvent instanceof ItemTransferred) {
                ItemTransferred itemTransferred = (ItemTransferred) domainEvent;
                if (itemTransferred.getFromItemBagId().equals(view.itemBagRendering())) {
                    view.removeItem(itemTransferred.getItemTransferred());
                } else if (itemTransferred.getToItemBagId().equals(view.itemBagRendering())) {
                    view.addItem(itemTransferred.getItemTransferred());
                }
            }
            if (domainEvent instanceof ItemEquipped) {
                ItemEquipped itemEquipped = (ItemEquipped) domainEvent;
                view.updateItem(itemEquipped.getItemEquipped());
                view.updateItem(itemEquipped.getItemUnequipped());
            }
            if (domainEvent instanceof ItemBagUpdated) {
                ItemBagUpdated itemBagUpdated = (ItemBagUpdated) domainEvent;
                view.replaceItems(itemBagUpdated.getItemBagUpdated());
            }
        }
    }
}

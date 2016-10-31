package com.jam01.littlelight.adapter.android.presentation.inventory;

import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.ItemBagUpdated;
import com.jam01.littlelight.domain.inventory.ItemEquipped;
import com.jam01.littlelight.domain.inventory.ItemTransferred;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    public void bindView(InventoryView inventoryView) {
        this.view = inventoryView;
    }


    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        subscriptions.add(Observable.create(new Observable.OnSubscribe<Inventory>() {
            @Override
            public void call(Subscriber<? super Inventory> subscriber) {
                subscriber.onNext(service.ofAccount(anAccountId));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnInventoryAction(), new OnErrorAction()));

        subscriptions.add(service.subscribeToInventoryEvents(anAccountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DomainEvent>() {
                               @Override
                               public void call(DomainEvent domainEvent) {
                                   if (domainEvent instanceof ItemTransferred) {
                                       ItemTransferred itemTransferred = (ItemTransferred) domainEvent;
                                       view.removeItem(itemTransferred.getItemTransferred(), itemTransferred.getFromItemBagId());
                                       view.addItem(itemTransferred.getItemTransferred(), itemTransferred.getToItemBagId());
                                   }
                                   if (domainEvent instanceof ItemEquipped) {
                                       ItemEquipped itemEquipped = (ItemEquipped) domainEvent;
                                       view.updateItem(itemEquipped.getItemEquipped(), itemEquipped.getOnBagId());
                                       view.updateItem(itemEquipped.getItemUnequipped(), itemEquipped.getOnBagId());
                                   }
                                   if (domainEvent instanceof ItemBagUpdated) {
                                       ItemBagUpdated itemBagUpdated = (ItemBagUpdated) domainEvent;
                                       view.replaceItems(itemBagUpdated.getItemBagUpdated());
                                   }
                               }
                           }

                        , new OnErrorAction()
                ));
    }

    public void unbindView() {
        view.showLoading(false);
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
                    service.transferItem(instance.getItemId(), toItemBagId);
                }
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletedAction(), new OnErrorAction()));
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
                .subscribe(new CompletedAction(), new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        view.showError(throwable.getLocalizedMessage());
                    }
                }));
    }

    public void refresh(final AccountId anAccountId) {
        subscriptions.add(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                service.synchronizeInventoryOf(anAccountId);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletedAction(), new OnErrorAction()));
    }

    public interface InventoryView {
        void renderInventory(Inventory inventory);

        void renderEmblem(String url);

        void showLoading(boolean show);

        void showError(String localizedMessage);

        void removeItem(Item itemTransferred, String fromItemBagId);

        void addItem(Item itemTransferred, String toItemBagId);

        void updateItem(Item itemUnequipped, String onBagId);

        void replaceItems(ItemBag itemBagUpdated);
    }

    private class CompletedAction implements Action1<Void> {
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

    private class OnInventoryAction implements Action1<Inventory> {
        @Override
        public void call(Inventory inventory) {
            view.renderInventory(inventory);
            view.showLoading(false);
        }
    }
}


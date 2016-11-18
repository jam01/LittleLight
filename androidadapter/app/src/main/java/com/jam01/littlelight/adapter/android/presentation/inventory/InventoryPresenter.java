package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.util.Log;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.presentation.InventoryDPO;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventorySynced;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemEquipped;
import com.jam01.littlelight.domain.inventory.ItemTransferred;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendSynced;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jam01 on 9/5/16.
 */
public class InventoryPresenter {
    private final String TAG = getClass().getSimpleName();
    private InventoryService inventoryService;
    private LegendService legendService;
    private InventoryView view;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private OnErrorAction errorAction = new OnErrorAction();
    private OnCompletedAction completedAction = new OnCompletedAction();

    @Inject
    public InventoryPresenter(InventoryService inventoryService, LegendService legendService) {
        this.inventoryService = inventoryService;
        this.legendService = legendService;
    }

    public void bindView(InventoryView inventoryView) {
        this.view = inventoryView;
    }

    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        //Register for events
        subscriptions.add(inventoryService.subscribeToInventoryEvents(anAccountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
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
                    if (domainEvent instanceof InventorySynced) {
                        Inventory inventory = ((InventorySynced) domainEvent).getInventoryUpdated();
                        Legend legend = legendService.ofAccount(anAccountId);

                        if (legend != null) {
                            renderInventory(inventory, legend);
                        }
                    }
                }, errorAction));

        //Register for events
        subscriptions.add(legendService.subscribeToLegendEvents(anAccountId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
                    if (domainEvent instanceof LegendSynced) {
                        Legend legend = ((LegendSynced) domainEvent).getLegendUpdated();
                        Inventory inventory = inventoryService.ofAccount(anAccountId);

                        if (inventory != null) {
                            renderInventory(inventory, legend);
                        }
                    }
                }, errorAction));

        //Render dpo if we have necessary objects or trigger sync for missing ones
        Inventory inventory = inventoryService.ofAccount(anAccountId);
        Legend legend = legendService.ofAccount(anAccountId);
        if (inventory != null && legend != null)
            renderInventory(inventory, legend);
        else {
            if (legend == null)
                syncLegendAsync(anAccountId);
            if (inventory == null)
                syncInventoryAsync(anAccountId);
        }
    }

    private void renderInventory(Inventory inventory, Legend legend) {
        view.renderInventory(new InventoryDPO(inventory, legend));
        view.showLoading(false);
    }

    private void syncLegendAsync(AccountId anAccountId) {
        subscriptions.add(Completable.fromAction(() -> legendService.synchronizeLegendOf(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completedAction, errorAction));
    }

    public void unbindView() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
        view.showLoading(false);
        view = null;
    }

    public void sendItems(final List<Item> toTransfer, final String toItemBagId) {
        view.showLoading(true);
        // TODO: 11/10/16 Use streams to map items to their Ids whenever streams is available
        subscriptions.add(Completable.fromAction(() -> {
            for (Item instance : toTransfer)
                inventoryService.transferItem(instance.getItemId(), toItemBagId);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showLoading(false), errorAction));
    }

    public void equipItem(final Item item, final String characterId) {
        view.showLoading(true);
        subscriptions.add(Completable.fromAction(() -> inventoryService.equipItem(item.getBungieItemInstanceId(), characterId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completedAction, errorAction));
    }

    public void refresh(final AccountId anAccountId) {
        //Trigger sync for inventory, and legend only if necessary
        syncInventoryAsync(anAccountId);

        if (legendService.ofAccount(anAccountId) == null)
            syncLegendAsync(anAccountId);
    }

    private void syncInventoryAsync(AccountId anAccountId) {
        subscriptions.add(Completable.fromAction(() -> inventoryService.synchronizeInventoryOf(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completedAction, errorAction));
    }

    public interface InventoryView {
        void renderInventory(InventoryDPO inventory);

        void showLoading(boolean show);

        void showError(String localizedMessage);

        void removeItem(Item itemTransferred, String fromItemBagId);

        void addItem(Item itemTransferred, String toItemBagId);

        void updateItem(Item itemUnequipped, String onBagId);

    }

    private class OnCompletedAction implements Action {
        @Override
        public void run() throws Exception {
//            view.showLoading(false);
        }
    }

    private class OnErrorAction implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            view.showLoading(false);
            if (throwable instanceof BungieResponseException) {
                Log.d(TAG, "accept: BungieResponseException");
                throwable.printStackTrace();
                view.showError(throwable.getLocalizedMessage());
            } else if (throwable instanceof IllegalNetworkStateException) {
                Log.d(TAG, "accept: IllegalNetworkStatException");
                throwable.printStackTrace();
                view.showError("There was an error with that Network request, check you connectivity and try again");
            } else {
                Log.d(TAG, "accept: Something else");
                throwable.printStackTrace();
                throw new IllegalStateException("Something went wrong, Little Light will check the cause and address the issue.", throwable);
            }
        }
    }
}


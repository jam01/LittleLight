package com.jam01.littlelight.adapter.android.presentation.inventory;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.presentation.ExoticsDPO;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventorySynced;
import com.jam01.littlelight.domain.inventory.Item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jam01 on 11/8/16.
 */
public class ExoticsPresenter {
    private final String TAG = getClass().getSimpleName();
    private InventoryService service;
    private ExoticsView view;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private OnErrorAction errorAction = new OnErrorAction();
    private OnExoticsAction exoticsAction = new OnExoticsAction();

    @Inject
    public ExoticsPresenter(InventoryService inventoryService) {
        this.service = inventoryService;
    }

    public void bindView(ExoticsView exoticsView) {
        this.view = exoticsView;
    }


    public void unbindView() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
        view.showLoading(false);
        view = null;
    }

    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        subscriptions.add(service.subscribeToInventoryEvents(anAccountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
                    if (domainEvent instanceof InventorySynced) {
                        Inventory inventory = ((InventorySynced) domainEvent).getInventoryUpdated();
                        renderExotics(inventory.getExotics());
                    }
                }, errorAction));

        Inventory inventory = service.ofAccount(anAccountId);
        if (inventory != null)
            renderExotics(inventory.getExotics());
        else syncInventoryAsync(anAccountId);
    }

    private void renderExotics(List<Item> exotics) {
        subscriptions.add(Single.defer(() -> Single.just(new ExoticsDPO(exotics, service.exotics())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exoticsAction, errorAction));
    }

    public void refresh(AccountId anAccountId) {
        Inventory inventory = service.ofAccount(anAccountId);
        if (inventory != null)
            renderExotics(inventory.getExotics());
        else syncInventoryAsync(anAccountId);
    }

    private void syncInventoryAsync(AccountId anAccountId) {

    }

    public interface ExoticsView {

        void renderExotics(ExoticsDPO exoticsList);

        void showLoading(boolean show);

        void showError(String message);
    }

    private class OnExoticsAction implements Consumer<ExoticsDPO> {
        @Override
        public void accept(ExoticsDPO account) {
            view.renderExotics(account);
            view.showLoading(false);
        }
    }

    private class OnErrorAction implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            if (throwable instanceof BungieResponseException) {
                throwable.printStackTrace();
                view.showError(throwable.getLocalizedMessage());
                view.showLoading(false);
            } else if (throwable instanceof IllegalNetworkStateException) {
                throwable.printStackTrace();
                view.showError("There was an error with that Network request, check you connectivity and try again");
                view.showLoading(false);
            } else {
                throwable.printStackTrace();
                throw new IllegalStateException("Something went wrong, Little Light will check the cause and address the issue.", throwable);
            }
        }
    }
}

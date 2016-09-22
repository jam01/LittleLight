package com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.service.InventoryService;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.InventoryItem;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper.InventoryItemMapper;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 5/4/16.
 */
public class VaultInventoryPresenter {
    private VaultInventoryView view;
    private InventoryService service;
    private String Tag = getClass().getSimpleName();
    private Subscription subscription = Subscriptions.empty();
    private final String TAG = getClass().getSimpleName();

    public void onRefresh() {
        service.refreshData();
    }

    public void bindView(VaultInventoryView view) {
        this.view = view;
        this.service = InventoryService.getInstance(((Fragment) view).getActivity().getApplicationContext());

        view.showLoading(true);

        subscription = service.getVaultItems()
                .map(new Func1<List<Item>, List<InventoryItem>>() {
                    @Override
                    public List<InventoryItem> call(List<Item> items) {
                        return InventoryItemMapper.transform(items);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new VaultInventorySubscriber());
    }

    public void unbindView() {
        view = null;
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public interface VaultInventoryView {
        void showLoading(boolean bool);

        void renderItems(List<InventoryItem> item);

        void clearItems();
    }

    private class VaultInventorySubscriber extends Subscriber<List<InventoryItem>> {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted:");
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d(TAG, "onError: " + throwable.getMessage());
            throwable.printStackTrace();
        }

        @Override
        public void onNext(List<InventoryItem> items) {
            Log.d(TAG, "onNext: ");
            view.clearItems();
            view.renderItems(items);
            view.showLoading(false);
        }
    }
}

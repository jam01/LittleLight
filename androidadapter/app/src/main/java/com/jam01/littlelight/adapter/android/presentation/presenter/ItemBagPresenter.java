package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.ItemService;
import com.jam01.littlelight.domain.inventory.ItemBag;

import javax.inject.Inject;

/**
 * Created by jam01 on 9/24/16.
 */
public class ItemBagPresenter {
    private final String TAG = getClass().getSimpleName();
    private ItemService service;
    private ItemBagView view;
    private String Tag = getClass().getSimpleName();

    @Inject
    public ItemBagPresenter(ItemService service) {
        this.service = service;
    }

    public void onRefresh() {
    }

    public void bindView(ItemBagView view) {
        this.view = view;
        view.showLoading(true);
    }

    public void unbindView() {
        view = null;
    }

    public interface ItemBagView {
        void showLoading(boolean bool);

        void renderItems(ItemBag itemBag);

        void clearItems();
    }
}

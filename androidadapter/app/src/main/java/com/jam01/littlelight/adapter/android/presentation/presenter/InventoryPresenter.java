package com.jam01.littlelight.adapter.android.presentation.presenter;

import android.os.AsyncTask;

import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;

import javax.inject.Inject;

/**
 * Created by jam01 on 9/5/16.
 */
public class InventoryPresenter {
    private final String TAG = getClass().getSimpleName();
    InventoryService invService;
    private InventoryView view;

    @Inject
    public InventoryPresenter(InventoryService inventoryService) {
        this.invService = inventoryService;
    }

    public void bindView(InventoryView inventoryView, final AccountId anAccountId) {
        this.view = inventoryView;
        new AsyncTask<Void, Void, Inventory>() {
            @Override
            protected Inventory doInBackground(Void... voids) {
                return invService.ofAccount(anAccountId);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showLoading(true);
            }

            @Override
            protected void onPostExecute(Inventory aVoid) {
                super.onPostExecute(aVoid);
                view.showLoading(false);
                view.renderLegends(aVoid);
            }
        }.execute();
    }

    public void unbindView() {
        view = null;
    }

    public interface InventoryView {
        void renderLegends(Inventory inventory);

        void renderEmblem(String url);

        void showLoading(boolean show);
    }
}

package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.DestinyInventoryImportService;

/**
 * Created by jam01 on 9/5/16.
 */
public class InventoryPresenter {
    private final String TAG = getClass().getSimpleName();
    DestinyInventoryImportService service;

    public InventoryPresenter(DestinyInventoryImportService service) {
        this.service = service;

//
//        Callable<Void> task = new Callable<Void>() {
//            @Override
//            public Void call() throws Exception {
//                service.maintainInventoryFromAccount(new AccountId(124, ""));
//                return null;
//            }
//        };
//
//        try {
//            task.call();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}

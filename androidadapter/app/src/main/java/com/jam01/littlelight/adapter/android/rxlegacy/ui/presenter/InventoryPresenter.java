//package com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character;
//import com.jam01.littlelight.adapter.android.rxlegacy.domain.service.InventoryService;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper.CharacterLegendMapper;
//
//import java.util.Collection;
//
//import rx.Subscriber;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//import rx.subscriptions.Subscriptions;
//
///**
// * Created by jam01 on 4/28/16.
// */
//public class InventoryPresenter {
//    private InventoryView view;
//    private InventoryService service;
//    private String TAG = getClass().getSimpleName();
//    private Subscription subscription = Subscriptions.empty();
//
//
//    public InventoryPresenter() {
//    }
//
//    public void bindView(InventoryView inventoryView) {
//        this.view = inventoryView;
//        this.service = InventoryService.getInstance(((Activity) view).getApplicationContext());
//
//        view.showLoading(true);
//        subscription = service.getCharacters()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new LegendsSubscriber());
//    }
//
//    public void unbindView() {
//        view = null;
//        if (!subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//            Log.d(TAG, "unbindView: unsubscribing.");
//        }
//    }
//
//    public void onLegendSelected(CharacterLegend legend) {
//        view.renderEmblem(legend.getEmblemBackgroundPath());
//    }
//
//    public void onVaultSelected() {
//        view.renderEmblem(null);
//    }
//
//    public interface InventoryView {
//        void renderInventory(Collection<CharacterLegend> legendsCollection);
//        void renderEmblem(String url);
//        void showLoading(boolean show);
//    }
//
//    public class LegendsSubscriber extends Subscriber<Collection<Character>> {
//        @Override
//        public void onCompleted() {
//            Log.d(TAG, "onCompleted");
//        }
//
//        @Override
//        public void onError(Throwable throwable) {
//            view.showLoading(false);
//            Log.d(TAG, "onError: " + throwable.getMessage());
//            throwable.printStackTrace();
//        }
//
//        @Override
//        public void onNext(Collection<Character> characters) {
//            Log.d(TAG, "onNext");
//            view.renderInventory(CharacterLegendMapper.transform(characters));
//            view.showLoading(false);
//        }
//    }
//}

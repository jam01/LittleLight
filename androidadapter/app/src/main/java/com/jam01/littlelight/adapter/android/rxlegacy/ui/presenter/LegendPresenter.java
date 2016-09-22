//package com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character;
//import com.jam01.littlelight.adapter.android.rxlegacy.domain.service.LegendService;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper.CharacterLegendMapper;
//
//import java.util.Collection;
//import java.util.List;
//
//import rx.Subscriber;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//import rx.subscriptions.Subscriptions;
//
///**
// * Created by jam01 on 4/28/16.
// */
//public class LegendPresenter {
//    private LegendView view;
//    private LegendService service;
//    private String TAG = getClass().getSimpleName();
//    private Subscription subscription = Subscriptions.empty();
//
//    public LegendPresenter() {
//    }
//
//    public void bindView(LegendView view) {
//        this.view = view;
//        this.service = LegendService.getInstance(((Activity) view).getApplicationContext());
//
//        view.showLoading(true);
//        subscription = service.getCharacters()
//                .map(new Func1<List<Character>, List<CharacterLegend>>() {
//                    @Override
//                    public List<CharacterLegend> call(List<Character> characters) {
//                        return CharacterLegendMapper.transform(characters);
//                    }
//                })
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
//    public interface LegendView {
//        void renderLegends(Collection<CharacterLegend> legendsCollection);
//
//        void renderEmblem(String url);
//
//        void showLoading(boolean show);
//    }
//
//    public class LegendsSubscriber extends Subscriber<List<CharacterLegend>> {
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
//        public void onNext(List<CharacterLegend> characters) {
//            Log.d(TAG, "onNext");
//            view.renderLegends(characters);
//            view.showLoading(false);
//        }
//    }
//}

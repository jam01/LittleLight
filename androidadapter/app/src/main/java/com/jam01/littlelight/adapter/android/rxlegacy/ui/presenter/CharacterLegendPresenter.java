package com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.service.LegendService;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper.CharacterLegendMapper;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by jam01 on 5/4/16.
 */
public class CharacterLegendPresenter {
    private CharacterLegendView view;
    private LegendService service;
    private String TAG = getClass().getSimpleName();
    private Subscription subscription = Subscriptions.empty();

    public CharacterLegendPresenter() {
    }

    public void bindView(CharacterLegendView view, String charId) {
        this.view = view;
        this.service = LegendService.getInstance(((Fragment) view).getActivity().getApplicationContext());

        view.showLoading(true);
        subscription = service.getLegendById(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LegendCharacterSubscriber());
    }

    public void unbindView() {
        view = null;
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void onRefresh() {
//        view.showLoading(true);
        service.refreshData();
    }

    public interface CharacterLegendView {
        void renderCharacterLegend(CharacterLegend legendCharacter);

        void showLoading(boolean show);
    }

    @RxLogSubscriber
    public class LegendCharacterSubscriber extends Subscriber<Character> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d(TAG, "onError: " + throwable.getMessage());
            throwable.printStackTrace();
        }

        @Override
        public void onNext(Character character) {
            view.renderCharacterLegend(CharacterLegendMapper.transform(character));
            view.showLoading(false);
        }
    }
}

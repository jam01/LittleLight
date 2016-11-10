package com.jam01.littlelight.adapter.android.presentation.legend;

import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendUpdated;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jam01 on 9/5/16.
 */
public class LegendPresenter {
    private final String TAG = getClass().getSimpleName();
    private LegendService service;
    private LegendView view;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private OnErrorAction errorAction = new OnErrorAction();
    private OnLegendAction legendAction = new OnLegendAction();

    @Inject
    public LegendPresenter(LegendService legendService) {
        this.service = legendService;
    }

    public void bindView(LegendView inventoryView) {
        this.view = inventoryView;
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
                    if (domainEvent instanceof LegendUpdated) {
                        view.renderLegend(((LegendUpdated) domainEvent).getLegendUpdated());
                    }
                }, errorAction));


        subscriptions.add(Single.defer(() -> Single.just(service.ofAccount(anAccountId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(legendAction, errorAction));
    }

    public void unbindView() {
        view.showLoading(false);
        view = null;
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
    }

    public void refresh(final AccountId anAccountId) {
        subscriptions.add(Completable.fromAction(() -> service.synchronizeLegendOf(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showLoading(false), errorAction));

    }

    public interface LegendView {
        void renderLegend(Legend legend);

        void showLoading(boolean show);

        void showError(String localizedMessage);
    }

    private class OnLegendAction implements Consumer<Legend> {
        @Override
        public void accept(Legend account) {
            view.renderLegend(account);
            view.showLoading(false);
        }
    }

    private class OnErrorAction implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
            view.showError(throwable.getLocalizedMessage());
            view.showLoading(false);
        }
    }
}


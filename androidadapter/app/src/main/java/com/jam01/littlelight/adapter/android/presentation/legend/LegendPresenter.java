package com.jam01.littlelight.adapter.android.presentation.legend;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendSynced;

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

        subscriptions.add(service.subscribeToLegendEvents(anAccountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
                    if (domainEvent instanceof LegendSynced) {
                        view.renderLegend(((LegendSynced) domainEvent).getLegendUpdated());
                    }
                }, errorAction));


        subscriptions.add(Single.defer(() -> Single.just(service.ofAccount(anAccountId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(legendAction, errorAction));
    }

    public void unbindView() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
        view.showLoading(false);
        view = null;
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


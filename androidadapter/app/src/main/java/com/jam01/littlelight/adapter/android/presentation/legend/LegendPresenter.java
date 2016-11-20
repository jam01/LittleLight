package com.jam01.littlelight.adapter.android.presentation.legend;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendSynced;

import javax.inject.Inject;

import io.reactivex.Completable;
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
                        renderLegend(((LegendSynced) domainEvent).getLegendUpdated());
                    }
                }, errorAction));

        Legend legend = service.ofAccount(anAccountId);
        if (legend != null)
            renderLegend(legend);
        else
            syncLegendAsync(anAccountId);
    }

    private void renderLegend(Legend legendUpdated) {
        view.renderLegend(legendUpdated);
        view.showLoading(false);
    }

    public void refresh(AccountId anAccountId) {
        syncLegendAsync(anAccountId);
    }

    private void syncLegendAsync(AccountId anAccountId) {
        Completable.fromAction(() -> service.synchronizeLegendOf(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, t -> {
                    if (view != null)
                        errorAction.accept(t);
                });
    }

    public void unbindView() {
        subscriptions.clear();
        view.showLoading(false);
        view = null;
    }

    public interface LegendView {
        void renderLegend(Legend legend);

        void showLoading(boolean show);

        void showError(String localizedMessage);
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
                throw new IllegalStateException(TAG + ": Rethrowing an unhandled exception ", throwable);
            }
        }
    }
}
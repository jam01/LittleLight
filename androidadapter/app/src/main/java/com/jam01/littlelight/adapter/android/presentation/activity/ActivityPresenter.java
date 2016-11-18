package com.jam01.littlelight.adapter.android.presentation.activity;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.presentation.ActivitiesDPO;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.ActivityService;
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
 * Created by jam01 on 11/9/16.
 */
public class ActivityPresenter {
    private final String TAG = getClass().getSimpleName();
    private ActivityService service;
    private ActivityView view;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private OnErrorAction errorAction = new OnErrorAction();
    private OnActivityAction activityAction = new OnActivityAction();
    private LegendService legendService;

    @Inject
    public ActivityPresenter(ActivityService service, LegendService legendService) {
        this.service = service;
        this.legendService = legendService;
    }

    public void refresh(final AccountId anAccountId) {
        Legend legend = legendService.ofAccount(anAccountId);
        if (legend != null)
            renderAccount(legend, anAccountId);
        else syncLegendAsync(anAccountId);
    }

    public void bindView(ActivityView inventoryView) {
        this.view = inventoryView;
    }

    public void unbindView() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
        view.showLoading(false);
        view = null;
    }

    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        //Register for events
        subscriptions.add(legendService.subscribeToLegendEvents(anAccountId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
                    if (domainEvent instanceof LegendSynced) {
                        Legend legend = ((LegendSynced) domainEvent).getLegendUpdated();
                        renderAccount(legend, anAccountId);
                    }
                }));

        Legend legend = legendService.ofAccount(anAccountId);
        if (legend != null)
            renderAccount(legend, anAccountId);
        else syncLegendAsync(anAccountId);
    }

    private void syncLegendAsync(AccountId anAccountId) {
        subscriptions.add(Completable.fromAction(() -> legendService.synchronizeLegendOf(anAccountId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, errorAction));
    }

    private void renderAccount(Legend legend, AccountId anAccountId) {
        subscriptions.add(Single.defer(() -> Single.just(new ActivitiesDPO(legend, service.ofAccount(anAccountId))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityAction, errorAction));
    }

    public interface ActivityView {

        void renderActivity(ActivitiesDPO account);

        void showLoading(boolean show);

        void showError(String localizedMessage);
    }

    private class OnActivityAction implements Consumer<ActivitiesDPO> {
        @Override
        public void accept(ActivitiesDPO account) {
            view.renderActivity(account);
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

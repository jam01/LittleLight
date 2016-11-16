package com.jam01.littlelight.adapter.android.presentation.activity;

import com.jam01.littlelight.adapter.common.presentation.ActivitiesDPO;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.ActivityService;
import com.jam01.littlelight.application.LegendService;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import javax.inject.Inject;

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
        subscriptions.add(Single.defer(() -> Single.just(new ActivitiesDPO(legendService.ofAccount(anAccountId), service.ofAccount(anAccountId))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityAction, errorAction));
    }

    public void bindView(ActivityView inventoryView) {
        this.view = inventoryView;
    }

    public void unbindView() {
        view.showLoading(false);
        view = null;
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
    }

    public void onStart(final AccountId anAccountId) {
        view.showLoading(true);
        if (subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        subscriptions.add(Single.defer(() -> Single.just(new ActivitiesDPO(legendService.ofAccount(anAccountId), service.ofAccount(anAccountId))))
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
            } else {
                throwable.printStackTrace();
                throw new IllegalStateException("Something went wrong, Little Light will check the cause and address the issue.", throwable);
            }
        }
    }
}

package com.jam01.littlelight.adapter.android.presentation.user;

import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountUpdated;
import com.jam01.littlelight.domain.identityaccess.User;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jam01 on 9/23/16.
 */
public class UserPresenter {
    private final String TAG = this.getClass().getSimpleName();
    private MainView view;
    private UserService service;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private OnErrorAction errorAction = new OnErrorAction();

    @Inject
    public UserPresenter(final UserService service) {
        this.service = service;
    }

    public void bindView(MainView mainView) {
        view = mainView;
    }

    public void unbindView() {
        if (!subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
        view.showLoading(false);
        view = null;
    }

    public void onStart() {
        if (subscriptions.isDisposed()) {
            subscriptions = new CompositeDisposable();
        }

        subscriptions.add(service.subscribeToUserEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(domainEvent -> {
                    if (domainEvent instanceof AccountUpdated) {
                        view.updateAccount(((AccountUpdated) domainEvent).getAccountUpdated());
                    } else if (domainEvent instanceof AccountCredentialsExpired) {
                        view.showWebSignInForUpdatingCredentials(((AccountCredentialsExpired) domainEvent).getExpiredAccount().withId());
                    }
                }, errorAction));

        subscriptions.add(Completable.fromAction(() -> service.synchronizeUserAccounts())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, errorAction));

        view.setUser(service.getUser());
        if (service.userAccounts().isEmpty()) {
            view.showWebSignIn();
        }
    }

    public void onAddAccount() {
        view.showWebSignIn();
    }

    public void onRemoveAccount(Account account) {
        service.unregister(account.withId());
        view.removeAccount(account);
    }

    public void onWebSignInCompleted(final int membershipType, final String[] cookies) {
        subscriptions.add(Single.defer(() -> Single.just(service.registerFromCredentials(membershipType, cookies)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(account -> {
                    view.addAccount(account);
                    view.displayAccount(account);
                    view.showLoading(false);
                }, errorAction));
        view.showLoading(true);
    }

    public void onWebCredentialsUpdated(final AccountId accountId, final String[] cookies) {
        subscriptions.add(Completable.fromAction(() -> service.updateCredentials(accountId, cookies))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showLoading(false);
                }, errorAction));
        view.showLoading(true);
    }

    public interface MainView {
        void showWebSignInForUpdatingCredentials(AccountId accountId);

        void showWebSignIn();

        void setUser(User user);

        void showError(String localizedMessage);

        void updateAccount(Account accountUpdated);

        void removeAccount(Account account);

        void addAccount(Account accountToAdd);

        void displayAccount(Account account);

        void showLoading(boolean bool);
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
                throw new IllegalStateException(throwable);
            }
        }
    }
}

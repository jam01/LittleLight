package com.jam01.littlelight.adapter.android.presentation.user;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.adapter.android.utils.IllegalNetworkStateException;
import com.jam01.littlelight.adapter.common.service.BungieResponseException;
import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountUpdated;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<AccountId> expiredQueue = new LinkedHashSet<>();
    private boolean updatingCredentials = false;

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
                        expiredQueue.add(((AccountCredentialsExpired) domainEvent).getExpiredAccount().withId());
                        handleExpiredQueue();
                    }
                }, errorAction));

        subscriptions.add(Completable.fromAction(() -> service.synchronizeUserAccounts())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, errorAction));

        view.setUser(service.getUser());
        if (service.userAccounts().isEmpty()) {
            onAddAccount();
        }
    }

    public void handleExpiredQueue() {
        Iterator iterator = expiredQueue.iterator();
        if (!updatingCredentials && iterator.hasNext()) {
            updatingCredentials = true;
            view.showUpdateCredentialsNowDialog(service.getUser().ofId((AccountId) iterator.next()).withName());
        }
    }

    public void onUpdateCredentialsNow(boolean now) {
        Iterator iterator = expiredQueue.iterator();
        if (now) {
            AccountId accountId = (AccountId) iterator.next();
            view.showWebSignIn(accountId.withMembershipType() == 2 ? Endpoints.PSN_AUTH_URL : Endpoints.XBOX_AUTH_URL, new AccountCredentialsCallback() {
                @Override
                void onDismissed() {
                    iterator.remove();
                    updatingCredentials = false;
                    handleExpiredQueue();
                }

                @Override
                void onResult(String[] cookies) {
                    service.updateCredentials(accountId, cookies);
                    iterator.remove();
                    updatingCredentials = false;
                    handleExpiredQueue();
                }
            });
        } else {
            iterator.next();
            iterator.remove();
            updatingCredentials = false;
            handleExpiredQueue();
        }
    }

    public void onAddAccount() {
        view.showChoosePlatformDialog(new String[]{"PlayStation Network", "Xbox Live"});
    }

    public void onRemoveAccount(Account account) {
        service.unregister(account.withId());
        view.removeAccount(account);
    }

    public void onPlatformChosen(int platform) {
        view.showWebSignIn(platform == 0 ? Endpoints.PSN_AUTH_URL : Endpoints.XBOX_AUTH_URL, new AccountCredentialsCallback() {
            @Override
            void onDismissed() {
                return;
            }

            @Override
            void onResult(String[] cookies) {
                subscriptions.add(Single.defer(() -> Single.just(service.registerFromCredentials(platform == 0 ? 2 : 1, cookies)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(account -> {
                            view.addAccount(account);
                            view.displayAccount(account);
                            view.showLoading(false);
                        }, errorAction));
                view.showLoading(true);
            }
        });
    }

    public interface MainView {
        void showChoosePlatformDialog(String[] platforms);

        void setUser(User user);

        void showError(String localizedMessage);

        void updateAccount(Account accountUpdated);

        void removeAccount(Account account);

        void addAccount(Account accountToAdd);

        void displayAccount(Account account);

        void showLoading(boolean bool);

        void showWebSignIn(String url, AccountCredentialsCallback accountCredentialsCallback);

        void showUpdateCredentialsNowDialog(String accountName);
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

    public abstract class AccountCredentialsCallback {
        abstract void onDismissed();

        abstract void onResult(String[] cookies);
    }
}

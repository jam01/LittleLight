package com.jam01.littlelight.adapter.android.rxlegacy.data.repository;

import com.jam01.littlelight.adapter.android.rxlegacy.data.RetrofitNetworkClient;
import com.bungie.netplatform.Account;
import com.bungie.netplatform.BungieResponse;
import com.bungie.netplatform.Character;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Timestamped;
import rx.subjects.PublishSubject;

/**
 * Created by jam01 on 4/12/16. Repository for Account Entity.
 * It holds an observable to be shared by all subscribers and holds a replay cache.
 * See http://blog.grio.com/2016/01/rxandroid-polling-and-other.html
 */
public class AccountRepository {
    private static AccountRepository mInstance;
    private final PublishSubject<Void> refreshData = PublishSubject.create();
    private final String TAG = getClass().getSimpleName();
    private Observable<Timestamped<Account>> timestampedObservable;

    //Singleton magic
    public static synchronized AccountRepository getInstance() {
        if (mInstance == null) {
            mInstance = new AccountRepository();
        }
        return mInstance;
    }

    //This triggers a repeat on the get() Observable
    public void refreshData() {
        refreshData.onNext(null);
    }

    //This observable returns a HOT Observable<Timestamped<AccountEntity>>
    //The replay(1) operator implements caching for us
    private synchronized Observable<Timestamped<Account>> get(int membershipType, String membershipId) {
        if (timestampedObservable == null) {
            timestampedObservable = getFromNetwork(membershipType, membershipId)
                    // We want to repeat the network request whenever someone requests it
                    .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                        @Override
                        public Observable<?> call(Observable<? extends Void> observable) {
                            //This prevents multiple refresh requests from firing network requests
                            //This allows only the first refresh request in a 2 second window to pass
                            return refreshData.throttleFirst(2, TimeUnit.SECONDS);
                        }
                    })
                    .timestamp()
                    .replay(1)
                    .autoConnect();
        }
        return timestampedObservable;
    }

    public Observable<Account> get(int membershipType, String membershipId, final int maxAge) {
        return get(membershipType, membershipId)
                .skipWhile(new Func1<Timestamped<Account>, Boolean>() {
                    @Override
                    public Boolean call(Timestamped<Account> accountEntityTimestamped) {
                        //Requesting a new emission if the item in replay cache is too old
                        if (accountEntityTimestamped.getTimestampMillis() < System.currentTimeMillis() - maxAge)
                            refreshData.onNext(null);
                        return accountEntityTimestamped.getTimestampMillis() < System.currentTimeMillis() - maxAge;
                    }
                })
                .map(new Func1<Timestamped<Account>, Account>() {
                    @Override
                    public Account call(Timestamped<Account> accountEntityTimestamped) {
                        return accountEntityTimestamped.getValue();
                    }
                });
    }

    public Observable<List<Character>> getAllCharacters(int membershipType, String membershipId, int maxAge) {
        return get(membershipType, membershipId, maxAge)
                .map(new Func1<Account, List<Character>>() {
                    @Override
                    public List<Character> call(Account account) {
                        return account.getCharacters();
                    }
                });
    }

    public Observable<Character> getCharacterById(int membershipType, String membershipId, final String charId, int maxAge) {
        return getAllCharacters(membershipType, membershipId, maxAge)
                .map(new Func1<List<Character>, Character>() {
                    @Override
                    public Character call(List<Character> characterEntities) {
                        for (Character character : characterEntities) {
                            if (character.getCharacterBase().getCharacterId().equals(charId))
                                return character;
                        }
                        return null;
                    }
                });
    }

    private Observable<Account> getFromNetwork(int membershipType, String membershipId) {
        return RetrofitNetworkClient.getInstance().getBungieApi().requestAccount(String.valueOf(membershipType), membershipId)
                .repeat(3)
                .first(new Func1<BungieResponse<Account>, Boolean>() {
                    @Override
                    public Boolean call(BungieResponse<Account> accountEntityBungieResponse) {
                        return accountEntityBungieResponse.getErrorCode() == 1;
                    }
                })
                .map(new Func1<BungieResponse<Account>, Account>() {
                    @Override
                    public Account call(BungieResponse<Account> accountEntityBungieResponse) {
                        return accountEntityBungieResponse.getResponse().getData();
                    }
                });
//                .doOnNext(new Action1<AccountEntity>() {
//                    @Override
//                    public void call(AccountEntity accountEntity) {
//                        //Saving the network response in memory
//                        mAccountEntityTimestamped = new Timestamped<>(System.currentTimeMillis(), accountEntity);
//                    }
//                });
    }

    /**
     * This implementation of get() holds an observable to be shared by all subscribers and manually holds a cache in memory for 5 seconds
     * Keeping it for reference purposes. Not as clean as other implementation.
     * See https://github.com/dlew/rxjava-multiple-sources-sample
     */

//    @Deprecated
//    @RxLogObservable
//    public Observable<AccountEntity> getAccountWithManualCache(int membershipType, String membershipId) {
//        if (accountEntity == null) {
//            Log.d(TAG, "get: first subscriber");
//            accountEntity = Observable.concat(
//                    getFromMemory(500),
//                    getFromNetwork(membershipType, membershipId))
//                    .first(new Func1<AccountEntity, Boolean>() {
//                        @Override
//                        public Boolean call(AccountEntity accountEntity) {
//                            return accountEntity != null;
//                        }
//                    })
//                    .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
//                        @Override
//                        public Observable<?> call(Observable<? extends Void> observable) {
//                            //Delaying 50 milliseconds to make sure the subscribers finish subscribing before repeating
//                            return refreshData.delay(50, TimeUnit.MILLISECONDS);
//                        }
//                    })
//                    .share();
//        } else {
//            //This means a new subscription so let's trigger a repeat
//            Log.d(TAG, "get: new subscriber");
//            refreshData.onNext(null);
//        }
//        return accountEntity;
//    }


//    @RxLogObservable
//    private Observable<AccountEntity> getFromMemory(final int maxAge) {
//        //Using defer to recreate the Observable every time, this way we make sure we check the age every time
//        return Observable.defer(new Func0<Observable<AccountEntity>>() {
//            @Override
//            public Observable<AccountEntity> call() {
//                if ((mAccountEntityTimestamped != null && mAccountEntityTimestamped.getTimestampMillis() >= System.currentTimeMillis() - maxAge)) {
//                    Log.d(TAG, "call: was within age");
//                    return Observable.just(mAccountEntityTimestamped.getValue());
//                } else {
//                    Log.d(TAG, "call: was not within age");
//                    return Observable.empty();
//                }
//            }
//        });
//    }
}
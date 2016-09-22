package com.jam01.littlelight.adapter.android.rxlegacy.data.repository;

import android.util.Log;

import com.jam01.littlelight.adapter.android.rxlegacy.data.RetrofitNetworkClient;
import com.bungie.netplatform.Account;
import com.bungie.netplatform.BungieResponse;
import com.bungie.netplatform.Character;
import com.bungie.netplatform.CharacterInventory;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.InventoryEntity;
import com.bungie.netplatform.Vault;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Timestamped;
import rx.subjects.PublishSubject;

/**
 * Created by jam01 on 4/12/16.
 */
public class InventoryRepository {
    private static InventoryRepository mInstance;
    private final String TAG = getClass().getSimpleName();
    private final PublishSubject<Void> refreshData = PublishSubject.create();
    private Observable<Timestamped<InventoryEntity>> timestampedObservable;

    private InventoryRepository() {
        Log.d(TAG, "InventoryRepository: new instance");
    }

    //Singleton magic
    public static synchronized InventoryRepository getInstance() {
        if (mInstance == null) {
            mInstance = new InventoryRepository();
        }
        return mInstance;
    }


    //This triggers a repeat on the get() Observable
    public void refreshData() {
        refreshData.onNext(null);
    }

    private Observable<Vault> getVaultFromNetwork(int membershipType) {
        return RetrofitNetworkClient.getInstance().getBungieApi().requestVault(String.valueOf(membershipType))
                .retry(2)
                .repeat(3)
                .first(new Func1<BungieResponse<Vault>, Boolean>() {
                    @Override
                    public Boolean call(BungieResponse<Vault> inventoryEntityBungieResponse) {
                        //Stop repeating once we have a successful response code
                        return inventoryEntityBungieResponse.getErrorCode() == 1;
                    }
                })
                .map(new Func1<BungieResponse<Vault>, Vault>() {
                    @Override
                    public Vault call(BungieResponse<Vault> itemBungieResponse) {
                        return itemBungieResponse.getResponse().getData();
                    }
                });
    }

    private Observable<CharacterInventory> getCharacterFromNetwork(int membershipType, String membershipId, final String characterId) {
        return RetrofitNetworkClient.getInstance().getBungieApi().requestCharacterInventory(String.valueOf(membershipType), membershipId, characterId)
                .retry(2)
                .repeat(3)
                .first(new Func1<BungieResponse<CharacterInventory>, Boolean>() {
                    @Override
                    public Boolean call(BungieResponse<CharacterInventory> inventoryEntityBungieResponse) {
                        //Stop repeating once we have a successful response code
                        return inventoryEntityBungieResponse.getErrorCode() == 1;
                    }
                })
                .map(new Func1<BungieResponse<CharacterInventory>, CharacterInventory>() {
                    @Override
                    public CharacterInventory call(BungieResponse<CharacterInventory> characterInventoryEntityBungieResponse) {
                        characterInventoryEntityBungieResponse.getResponse().getData().setCharacterId(characterId);
                        return characterInventoryEntityBungieResponse.getResponse().getData();
                    }
                });
    }

    /**
     * synchronized method to prevent requests from different threads creating different instances
     *
     * @param account the account for which to retrieve character and vault inventory
     * @return a HOT observable that emits cached timestamped inventory and refreshes inventory on request
     */
    private synchronized Observable<Timestamped<InventoryEntity>> get(final Account account) {
        if (timestampedObservable == null) {
            timestampedObservable =
                    Observable.zip(
                            Observable.from(account.getCharacters())
                                    .flatMap(new Func1<Character, Observable<CharacterInventory>>() {
                                        @Override
                                        public Observable<CharacterInventory> call(Character character) {
                                            return getCharacterFromNetwork(account.getMembershipType().intValue(), account.getMembershipId(), character.getCharacterBase().getCharacterId());
                                        }
                                    }).toList(),
                            getVaultFromNetwork(account.getMembershipType().intValue()),
                            new Func2<List<CharacterInventory>, Vault, InventoryEntity>() {
                                @Override
                                public InventoryEntity call(List<CharacterInventory> characterInventoryEntity, Vault item) {
                                    return new InventoryEntity(characterInventoryEntity, item);
                                }
                            })
                            // We want to repeat the network request whenever someone requests it
                            .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                                @Override
                                public Observable<?> call(Observable<? extends Void> observable) {
                                    // This prevents multiple refresh requests from firing network requests
                                    // This allows only the first refresh request in a 2 second window to pass
                                    return refreshData.throttleFirst(2, TimeUnit.SECONDS);
                                }
                            })
                            .timestamp()
                            .replay(1)
                            .autoConnect();
        }
        return timestampedObservable;
    }

    public Observable<InventoryEntity> get(Account account, final int maxAge) {
        return get(account)
                .skipWhile(new Func1<Timestamped<InventoryEntity>, Boolean>() {
                    @Override
                    public Boolean call(Timestamped<InventoryEntity> inventoryEntityTimestamped) {
                        //Requesting a new emission if the item in replay cache is too old
                        if (inventoryEntityTimestamped.getTimestampMillis() < System.currentTimeMillis() - maxAge) {
                            Log.d(TAG, "call: was not within age. Requesting a refresh");
                            refreshData.onNext(null);
                        }
                        return inventoryEntityTimestamped.getTimestampMillis() < System.currentTimeMillis() - maxAge;
                    }
                })
                .map(new Func1<Timestamped<InventoryEntity>, InventoryEntity>() {
                    @Override
                    public InventoryEntity call(Timestamped<InventoryEntity> inventoryEntityTimestamped) {
                        return inventoryEntityTimestamped.getValue();
                    }
                });
    }

    public Observable<CharacterInventory> getCharacterById(Account account, final String charId, int maxAge) {
        return get(account, maxAge)
                .map(new Func1<InventoryEntity, CharacterInventory>() {
                    @Override
                    public CharacterInventory call(InventoryEntity inventoryEntity) {
                        for (CharacterInventory inventory : inventoryEntity.getCharacterInventory()) {
                            if (inventory.getCharacterId().equals(charId)) {
                                return inventory;
                            }
                        }
                        return null;
                    }
                });
    }

    public Observable<Vault> getVault(Account account, int maxAge) {
        return get(account, maxAge)
                .map(new Func1<InventoryEntity, Vault>() {
                    @Override
                    public Vault call(InventoryEntity inventoryEntity) {
                        return inventoryEntity.getVault();
                    }
                });
    }

    public Observable<Boolean> transferItem() {
        return null;
    }
}
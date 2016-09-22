package com.jam01.littlelight.adapter.android.rxlegacy.domain.service;

import android.content.Context;
import android.util.Log;

import com.bungie.netplatform.Account;
import com.bungie.netplatform.Character;
import com.bungie.netplatform.CharacterInventory;
import com.bungie.netplatform.ItemDefinition;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.AccountRepository;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.CredentialsRepository;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.InventoryRepository;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.ItemDefinitionRepository;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.mapper.CharacterMapper;
import com.jam01.littlelight.adapter.android.inventory.service.ItemTranslator;
import com.bungie.netplatform.Equippable;
import com.bungie.netplatform.ItemInstance;
import com.bungie.netplatform.Vault;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by jam01 on 4/28/16.
 */
public class InventoryService {
    private static InventoryService mInstance;
    private AccountRepository accountRepository;
    private InventoryRepository inventoryRepository;
    private ItemDefinitionRepository itemDefinitionRepository;
    private CredentialsRepository credentialsRepository;
    private String TAG = getClass().getSimpleName();

    private InventoryService(Context applicationContext) {
        Log.d(TAG, "InventoryService: new instance");
        credentialsRepository = CredentialsRepository.getInstance(applicationContext);
        inventoryRepository = InventoryRepository.getInstance();
        accountRepository = AccountRepository.getInstance();
        itemDefinitionRepository = ItemDefinitionRepository.getInstance(applicationContext);
    }

    //Singleton magic
    public static synchronized InventoryService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InventoryService(context);
        }
        return mInstance;
    }

    public Observable<List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character>> getCharacters() {
        return accountRepository.getAllCharacters(credentialsRepository.getMembershipType(), credentialsRepository.getMembershipId(), 3600000)
                .first()
                .map(new Func1<List<Character>, List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character>>() {
                    @Override
                    public List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character> call(List<Character> characterEntities) {
                        return CharacterMapper.transform(characterEntities);
                    }
                });
    }

    public Observable<List<Item>> getVaultItems() {
        return accountRepository.get(credentialsRepository.getMembershipType(), credentialsRepository.getMembershipId(), 3600000)
                .flatMap(new Func1<Account, Observable<Vault>>() {
                    @Override
                    public Observable<Vault> call(Account account) {
                        return inventoryRepository.getVault(account, 5000);
                    }
                })
                .flatMap(new Func1<Vault, Observable<List<Item>>>() {
                    @Override
                    public Observable<List<Item>> call(Vault items) {
                        List<ItemInstance> list = new ArrayList<>();
                        for (com.bungie.netplatform.Item item : items.getBuckets()) {
                            list.addAll(item.getItems());
                        }
                        return getItemsFromInstances(list);
                    }
                });
    }

    public Observable<List<Item>> getItemsByCharId(final String characterId) {
        return accountRepository.get(credentialsRepository.getMembershipType(), credentialsRepository.getMembershipId(), 3600000)
                .flatMap(new Func1<Account, Observable<CharacterInventory>>() {
                    @Override
                    public Observable<CharacterInventory> call(Account account) {
                        return inventoryRepository.getCharacterById(account, characterId, 5000);
                    }
                })
                .flatMap(new Func1<CharacterInventory, Observable<List<Item>>>() {
                    @Override
                    public Observable<List<Item>> call(CharacterInventory characterInventoryEntity) {
                        List<ItemInstance> list = new ArrayList<>();
                        for (com.bungie.netplatform.Item items : characterInventoryEntity.getBuckets().getItem()) {
                            list.addAll(items.getItems());
                        }
                        for (Equippable equippables : characterInventoryEntity.getBuckets().getEquippable()) {
                            list.addAll(equippables.getItems());
                        }

                        return getItemsFromInstances(list);

                    }
                });
    }

    private Observable<List<Item>> getItemsFromInstances(final List<ItemInstance> list) {
        return Observable.zip(Observable.defer(new Func0<Observable<ItemInstance>>() {
                    @Override
                    public Observable<ItemInstance> call() {
                        return Observable.from(list);
                    }
                }),
                itemDefinitionRepository.getItemDefinition(list),
                new Func2<ItemInstance, ItemDefinition, Item>() {
                    @Override
                    public Item call(ItemInstance itemInstance, ItemDefinition itemDefinition) {
                        return ItemTranslator.transform(itemDefinition, itemInstance);
                    }
                })
                .toList();
    }

    public void refreshData() {
        inventoryRepository.refreshData();
    }
}

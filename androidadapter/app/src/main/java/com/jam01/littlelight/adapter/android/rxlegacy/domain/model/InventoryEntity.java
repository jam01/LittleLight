package com.jam01.littlelight.adapter.android.rxlegacy.domain.model;

import com.bungie.netplatform.CharacterInventory;
import com.bungie.netplatform.Vault;

import java.util.List;

/**
 * Created by jam01 on 5/19/16.
 */
public class InventoryEntity {
    final private List<CharacterInventory> characterInventory;
    final private Vault vault;

    public InventoryEntity(List<CharacterInventory> characterInventory, Vault vault) {
        this.characterInventory = characterInventory;
        this.vault = vault;
    }

    public List<CharacterInventory> getCharacterInventory() {
        return characterInventory;
    }

    public Vault getVault() {
        return vault;
    }
}

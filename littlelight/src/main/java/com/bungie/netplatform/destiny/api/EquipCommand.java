package com.bungie.netplatform.destiny.api;

/**
 * Created by jam01 on 9/22/16.
 */
public class EquipCommand {
    private final int membershipType;
    private final String itemId;
    private final String characterId;

    public EquipCommand(int membershipType, String itemId, String characterId) {
        this.membershipType = membershipType;
        this.itemId = itemId;
        this.characterId = characterId;
    }
}

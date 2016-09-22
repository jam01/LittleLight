package com.bungie.netplatform.destiny.api;

/**
 * Created by jam01 on 9/22/16.
 */
public class TransferCommand {
    private final int membershipType;
    private final String itemReferenceHash;
    private final String itemId;
    private final int stackSize;
    private final String characterId;
    private final boolean transferToVault;

    public TransferCommand(int membershipType, String itemReferenceHash, String itemId, int stackSize, String characterId, boolean transferToVault) {
        this.membershipType = membershipType;
        this.itemReferenceHash = itemReferenceHash;
        this.itemId = itemId;
        this.stackSize = stackSize;
        this.characterId = characterId;
        this.transferToVault = transferToVault;
    }
}

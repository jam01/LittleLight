package com.jam01.littlelight.adapter.android.rxlegacy.ui.model;

/**
 * Created by jam01 on 4/3/15.
 */
public class InventoryItem {
    private final String itemInstanceId;
    private final long itemHash;
    private final int stackSize;
    private final int maxStackSize;
    private final String icon;
    private final String itemName;
    private final int itemType;
    private final boolean isEquipped;
    private final long bucketTypeHash;
    private final int tierType;
    private final boolean isGridComplete;
    private final int damageType;
    private final int damage;
    private final int maxDamage;
    private final int classType;

    public InventoryItem(String itemInstanceId, long itemHash,int stackSize, int maxStackSize, String icon, String itemName, int itemType, boolean isEquipped, long bucketTypeHash, int tierType, boolean isGridComplete, int damageType, int damage, int maxDamage, int classType) {
        this.itemInstanceId = itemInstanceId;
        this.itemHash = itemHash;
        this.stackSize = stackSize;
        this.maxStackSize = maxStackSize;
        this.icon = icon;
        this.itemName = itemName;
        this.itemType = itemType;
        this.isEquipped = isEquipped;
        this.bucketTypeHash = bucketTypeHash;
        this.tierType = tierType;
        this.isGridComplete = isGridComplete;
        this.damageType = damageType;
        this.damage = damage;
        this.maxDamage = maxDamage;
        this.classType = classType;
    }

    public int getDamage() {
        return damage;
    }

    public int getDamageType() {
        return damageType;
    }

    public boolean getIsGridComplete() {
        return isGridComplete;
    }

    public int getTierType() {
        return tierType;
    }

    public long getBucketTypeHash() {
        return bucketTypeHash;
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }


    public String getItemInstanceId() {
        return itemInstanceId;
    }

    public long getItemHash() {
        return itemHash;
    }

    public int getStackSize() {
        return stackSize;
    }


    public int getMaxStackSize() {
        return maxStackSize;
    }


    public String getIcon() {
        return icon;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemType() {
        return itemType;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getClassType() {
        return classType;
    }
}

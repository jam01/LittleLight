package com.jam01.littlelight.domain.inventory;

/**
 * Created by jam01 on 11/7/16.
 */
public class ItemType {
    private final long bungieItemHash;
    private final String itemName;
    private final long bucketTypeHash;
    private final int maxStackSize;
    private final String iconPath;
    private final String tierType;
    private final String classType;
    private final boolean isEquippable;
    private final String itemType;
    private final String itemSubType;

    public ItemType(long bungieItemHash, String itemName, long bucketTypeHash, int maxStackSize, String iconPath, String tierType, String classType, boolean isEquippable, String itemType, String itemSubType) {
        this.bungieItemHash = bungieItemHash;
        this.itemName = itemName;
        this.bucketTypeHash = bucketTypeHash;
        this.maxStackSize = maxStackSize;
        this.iconPath = iconPath;
        this.tierType = tierType;
        this.classType = classType;
        this.isEquippable = isEquippable;
        this.itemType = itemType;
        this.itemSubType = itemSubType;
    }

    public long getBungieItemHash() {
        return bungieItemHash;
    }

    public String getItemName() {
        return itemName;
    }

    public long getBucketTypeHash() {
        return bucketTypeHash;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getTierType() {
        return tierType;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public String getClassType() {
        return classType;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemSubType() {
        return itemSubType;
    }
}
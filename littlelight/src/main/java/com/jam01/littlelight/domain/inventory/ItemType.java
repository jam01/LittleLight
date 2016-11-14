package com.jam01.littlelight.domain.inventory;

/**
 * Created by jam01 on 11/7/16.
 */
public class ItemType {
    private final long bungieItemHash;
    private final String itemName;
    private final long typeHash;
    private final int maxStackSize;
    private final String iconPath;
    private final String tierTypeName;
    private final String classType;
    private final boolean isEquippable;
    private final String itemType;
    private final String itemSubType;
    private final String itemSuperType;

    // TODO: 11/13/16 Make types a static array of some kind

    public ItemType(long bungieItemHash, String itemName, long typeHash, int maxStackSize, String iconPath, String tierTypeName, String classType, boolean isEquippable, String itemType, String itemSubType, String itemSuperType) {
        this.bungieItemHash = bungieItemHash;
        this.itemName = itemName;
        this.typeHash = typeHash;
        this.maxStackSize = maxStackSize;
        this.iconPath = iconPath;
        this.tierTypeName = tierTypeName;
        this.classType = classType;
        this.isEquippable = isEquippable;
        this.itemType = itemType;
        this.itemSubType = itemSubType;
        this.itemSuperType = itemSuperType;

    }

    public String getItemSuperType() {
        return itemSuperType;
    }

    public long getBungieItemHash() {
        return bungieItemHash;
    }

    public String getItemName() {
        return itemName;
    }

    public long getTypeHash() {
        return typeHash;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getTierTypeName() {
        return tierTypeName;
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
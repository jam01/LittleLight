package com.jam01.littlelight.domain.inventory;

/**
 * Created by jam01 on 5/11/16.
 */
public class Item {
    private final String itemId;
    private final String bungieItemInstanceId;
    private final boolean isGridComplete;
    private final String damageType;
    private final int damage;
    private final int maxDamage;
    private final ItemType itemType;
    private int stackSize;
    private boolean isEquipped;

    public Item(String itemId, String bungieItemInstanceId, int stackSize,
                boolean isEquipped, boolean isGridComplete, String damageType,
                int damage, int maxDamage, ItemType itemType) {
        this.itemId = itemId;
        this.bungieItemInstanceId = bungieItemInstanceId;
        this.stackSize = stackSize;
        this.isEquipped = isEquipped;
        this.isGridComplete = isGridComplete;
        this.damageType = damageType;
        this.damage = damage;
        this.maxDamage = maxDamage;
        this.itemType = itemType;
    }

    private Item(Builder builder) {
        itemId = builder.itemId;
        itemType = builder.itemType;
        bungieItemInstanceId = builder.itemInstanceId;
        stackSize = builder.stackSize;
        isEquipped = builder.isEquipped;
        isGridComplete = builder.isGridComplete;
        damageType = builder.damageType;
        damage = builder.damage;
        maxDamage = builder.maxDamage;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isGridComplete() {
        return isGridComplete;
    }

    public String getBungieItemInstanceId() {
        return bungieItemInstanceId;
    }

    public long getBungieItemHash() {
        return itemType.getBungieItemHash();
    }

    public int getStackSize() {
        return stackSize;
    }

    public int getMaxStackSize() {
        return itemType.getMaxStackSize();
    }

    public String getIconUrl() {
        return itemType.getIconPath();
    }

    public String getItemName() {
        return itemType.getItemName();
    }

    public boolean getEquipped() {
        return isEquipped;
    }

    public boolean getGridComplete() {
        return isGridComplete;
    }

    public String getDamageType() {
        return damageType;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public boolean isEquippable() {
        return itemType.isEquippable();
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public long getBungieBucketTypeHash() {
        return itemType.getBucketTypeHash();
    }

    public String getTierType() {
        return itemType.getTierType();
    }

    public String getItemType() {
        return itemType.getItemType();
    }

    public static class Builder {
        //Required parameters
        private final String itemId;
        private final String itemInstanceId;
        private final ItemType itemType;

        //Optional parameters
        private int stackSize;
        private boolean isEquipped;
        private boolean isGridComplete;
        private String damageType;
        private int damage;
        private int maxDamage;

        public Builder(String itemId, String itemInstanceId, ItemType itemType) {
            this.itemId = itemId;
            this.itemInstanceId = itemInstanceId;
            this.itemType = itemType;
        }

        public Builder stackSize(int val) {
            stackSize = val;
            return this;
        }

        public Builder isEquipped(boolean val) {
            isEquipped = val;
            return this;
        }

        public Builder isGridComplete(boolean val) {
            isGridComplete = val;
            return this;
        }

        public Builder damageType(String val) {
            damageType = val;
            return this;
        }

        public Builder damage(int val) {
            damage = val;
            return this;
        }

        public Builder maxDamage(int val) {
            maxDamage = val;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}

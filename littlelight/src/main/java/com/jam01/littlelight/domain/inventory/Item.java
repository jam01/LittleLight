package com.jam01.littlelight.domain.inventory;

/**
 * Created by jam01 on 5/11/16.
 */
public class Item {
    private final String itemInstanceId;
    private final long itemHash;
    private final int maxStackSize;
    private final String icon;
    private final String itemName;
    private final int itemType;
    private final boolean isEquippable;
    private final long bucketTypeHash;
    private final int tierType;
    private final boolean isGridComplete;
    private final int damageType;
    private final int damage;
    private final int maxDamage;
    private final int classType;
    private int stackSize;
    private boolean isEquipped;

    public Item(String itemInstanceId, long itemHash, int stackSize, int maxStackSize, String icon, String itemName, int itemType, boolean isEquippable, boolean isEquipped, long bucketTypeHash, int tierType, boolean isGridComplete, int damageType, int damage, int maxDamage, int classType) {
        this.itemInstanceId = itemInstanceId;
        this.itemHash = itemHash;
        this.stackSize = stackSize;
        this.maxStackSize = maxStackSize;
        this.icon = icon;
        this.itemName = itemName;
        this.itemType = itemType;
        this.isEquippable = isEquippable;
        this.isEquipped = isEquipped;
        this.bucketTypeHash = bucketTypeHash;
        this.tierType = tierType;
        this.isGridComplete = isGridComplete;
        this.damageType = damageType;
        this.damage = damage;
        this.maxDamage = maxDamage;
        this.classType = classType;
    }

    private Item(Builder builder) {
        itemInstanceId = builder.itemInstanceId;
        itemHash = builder.itemHash;
        itemName = builder.itemName;
        bucketTypeHash = builder.bucketTypeHash;
        stackSize = builder.stackSize;
        maxStackSize = builder.maxStackSize;
        icon = builder.icon;
        itemType = builder.itemType;
        isEquippable = builder.isEquippable;
        isEquipped = builder.isEquipped;
        tierType = builder.tierType;
        isGridComplete = builder.isGridComplete;
        damageType = builder.damageType;
        damage = builder.damage;
        maxDamage = builder.maxDamage;
        classType = builder.classType;
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

    public boolean getEquipped() {
        return isEquipped;
    }

    public long getBucketTypeHash() {
        return bucketTypeHash;
    }

    public int getTierType() {
        return tierType;
    }

    public boolean getGridComplete() {
        return isGridComplete;
    }

    public int getDamageType() {
        return damageType;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getClassType() {
        return classType;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public static class Builder {
        //Required parameters
        private final String itemInstanceId;
        private final long itemHash;
        private final String itemName;
        private final long bucketTypeHash;

        //Optional parameters
        private int stackSize;
        private int maxStackSize;
        private String icon = "";
        private int itemType;
        private boolean isEquippable;
        private boolean isEquipped;
        private int tierType;
        private boolean isGridComplete;
        private int damageType;
        private int damage;
        private int maxDamage;
        private int classType;

        public Builder(String itemInstanceId, long itemHash, String itemName, long bucketTypeHash) {
            this.itemInstanceId = itemInstanceId;
            this.itemHash = itemHash;
            this.itemName = itemName;
            this.bucketTypeHash = bucketTypeHash;
        }

        public Builder stackSize(int val) {
            stackSize = val;
            return this;
        }

        public Builder maxStackSize(int val) {
            maxStackSize = val;
            return this;
        }

        public Builder icon(String val) {
            icon = val;
            return this;
        }

        public Builder isEquippable(boolean val) {
            isEquippable = val;
            return this;
        }

        public Builder isEquipped(boolean val) {
            isEquipped = val;
            return this;
        }

        public Builder itemType(int val) {
            itemType = val;
            return this;
        }

        public Builder tierType(int val) {
            tierType = val;
            return this;
        }

        public Builder isGridComplete(boolean val) {
            isGridComplete = val;
            return this;
        }

        public Builder damageType(int val) {
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

        public Builder classType(int val) {
            classType = val;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}

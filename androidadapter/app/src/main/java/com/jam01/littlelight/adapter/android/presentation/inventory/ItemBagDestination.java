package com.jam01.littlelight.adapter.android.presentation.inventory;

/**
 * Created by jam01 on 11/15/16.
 */
public class ItemBagDestination {
    public String name;
    public String iconPath;
    public String emblemPath;
    public String itemBagId;

    public ItemBagDestination(String name, String iconPath, String emblemPath, String itemBagId) {
        this.name = name;
        this.iconPath = iconPath;
        this.emblemPath = emblemPath;
        this.itemBagId = itemBagId;
    }


    public ItemBagDestination(String name, String iconPath, String emblemPath) {
        this.name = name;
        this.iconPath = iconPath;
        this.emblemPath = emblemPath;
    }
}

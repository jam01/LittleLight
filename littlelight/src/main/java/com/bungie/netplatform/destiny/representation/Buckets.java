package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Buckets {

    @Expose
    private List<Item> Item = new ArrayList<Item>();
    @Expose
    private List<Invisible> Invisible = new ArrayList<Invisible>();
    @Expose
    private List<com.bungie.netplatform.destiny.representation.Currency> Currency = new ArrayList<com.bungie.netplatform.destiny.representation.Currency>();
    @Expose
    private List<Equippable> Equippable = new ArrayList<Equippable>();

    /**
     * @return The Item
     */
    public List<Item> getItem() {
        return Item;
    }

    /**
     * @param Item The Item
     */
    public void setItem(List<Item> Item) {
        this.Item = Item;
    }

    /**
     * @return The Invisible
     */
    public List<Invisible> getInvisible() {
        return Invisible;
    }

    /**
     * @param Invisible The Invisible
     */
    public void setInvisible(List<Invisible> Invisible) {
        this.Invisible = Invisible;
    }

    /**
     * @return The Currency
     */
    public List<com.bungie.netplatform.destiny.representation.Currency> getCurrency() {
        return Currency;
    }

    /**
     * @param Currency The Currency
     */
    public void setCurrency(List<com.bungie.netplatform.destiny.representation.Currency> Currency) {
        this.Currency = Currency;
    }

    /**
     * @return The Equippable
     */
    public List<Equippable> getEquippable() {
        return Equippable;
    }

    /**
     * @param Equippable The Equippable
     */
    public void setEquippable(List<Equippable> Equippable) {
        this.Equippable = Equippable;
    }
}

package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CharacterInventory {

    @Expose
    private Buckets buckets;
    @Expose
    private List<com.bungie.netplatform.destiny.representation.Currency_> currencies = new ArrayList<>();
    private String characterId;

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    /**
     * @return The buckets
     */
    public Buckets getBuckets() {
        return buckets;
    }

    /**
     * @return The currencies
     */
    public List<com.bungie.netplatform.destiny.representation.Currency_> getCurrencies() {
        return currencies;
    }

}

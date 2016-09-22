package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Inventory {

    @Expose
    private Buckets buckets;
    @Expose
    private List<com.bungie.netplatform.destiny.representation.Currency_> currencies = new ArrayList<com.bungie.netplatform.destiny.representation.Currency_>();

    /**
     * @return The buckets
     */
    public Buckets getBuckets() {
        return buckets;
    }

    /**
     * @param buckets The buckets
     */
    public void setBuckets(Buckets buckets) {
        this.buckets = buckets;
    }

    /**
     * @return The currencies
     */
    public List<com.bungie.netplatform.destiny.representation.Currency_> getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies The currencies
     */
    public void setCurrencies(List<com.bungie.netplatform.destiny.representation.Currency_> currencies) {
        this.currencies = currencies;
    }
}

package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Vault {

    @Expose
    private List<Item> buckets = new ArrayList<Item>();

    /**
     * @return The buckets
     */
    public List<Item> getBuckets() {
        return buckets;
    }

    /**
     * @param buckets The buckets
     */
    public void setBuckets(List<Item> buckets) {
        this.buckets = buckets;
    }
}

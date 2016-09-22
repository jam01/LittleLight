package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Currency {

    @Expose
    private List<ItemInstance> items = new ArrayList<ItemInstance>();
    @Expose
    private Long bucketHash;

    /**
     * @return The items
     */
    public List<ItemInstance> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<ItemInstance> items) {
        this.items = items;
    }

    /**
     * @return The bucketHash
     */
    public Long getBucketHash() {
        return bucketHash;
    }

    /**
     * @param bucketHash The bucketHash
     */
    public void setBucketHash(Long bucketHash) {
        this.bucketHash = bucketHash;
    }
}

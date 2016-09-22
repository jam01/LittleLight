package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Equipment {

    @Expose
    private Long itemHash;
    @Expose
    private List<Dye> dyes = new ArrayList<Dye>();

    /**
     * @return The itemHash
     */
    public Long getItemHash() {
        return itemHash;
    }

    /**
     * @param itemHash The itemHash
     */
    public void setItemHash(Long itemHash) {
        this.itemHash = itemHash;
    }

    /**
     * @return The dyes
     */
    public List<Dye> getDyes() {
        return dyes;
    }

    /**
     * @param dyes The dyes
     */
    public void setDyes(List<Dye> dyes) {
        this.dyes = dyes;
    }

}

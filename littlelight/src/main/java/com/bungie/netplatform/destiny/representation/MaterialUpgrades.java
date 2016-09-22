
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MaterialUpgrades {

    @SerializedName("itemSoidsUpgradable")
    @Expose
    private List<String> itemSoidsUpgradable = new ArrayList<String>();
    @SerializedName("materialItemHash")
    @Expose
    private Long materialItemHash;
    @SerializedName("activityBundleHash")
    @Expose
    private Long activityBundleHash;

    /**
     * @return The itemSoidsUpgradable
     */
    public List<String> getItemSoidsUpgradable() {
        return itemSoidsUpgradable;
    }

    /**
     * @param itemSoidsUpgradable The itemSoidsUpgradable
     */
    public void setItemSoidsUpgradable(List<String> itemSoidsUpgradable) {
        this.itemSoidsUpgradable = itemSoidsUpgradable;
    }

    /**
     * @return The materialItemHash
     */
    public Long getMaterialItemHash() {
        return materialItemHash;
    }

    /**
     * @param materialItemHash The materialItemHash
     */
    public void setMaterialItemHash(Long materialItemHash) {
        this.materialItemHash = materialItemHash;
    }

    /**
     * @return The activityBundleHash
     */
    public Long getActivityBundleHash() {
        return activityBundleHash;
    }

    /**
     * @param activityBundleHash The activityBundleHash
     */
    public void setActivityBundleHash(Long activityBundleHash) {
        this.activityBundleHash = activityBundleHash;
    }
}

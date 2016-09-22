
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SaleItem {

    @SerializedName("itemStatus")
    @Expose
    private Long itemStatus;
    @SerializedName("unlockStatuses")
    @Expose
    private List<UnlockStatus> unlockStatuses = new ArrayList<UnlockStatus>();
    @SerializedName("requiredUnlockFlags")
    @Expose
    private List<Long> requiredUnlockFlags = new ArrayList<Long>();
    @SerializedName("vendorItemIndex")
    @Expose
    private Long vendorItemIndex;
    @SerializedName("costs")
    @Expose
    private List<Object> costs = new ArrayList<Object>();
    @SerializedName("item")
    @Expose
    private Item item;

    /**
     * @return The itemStatus
     */
    public Long getItemStatus() {
        return itemStatus;
    }

    /**
     * @param itemStatus The itemStatus
     */
    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * @return The unlockStatuses
     */
    public List<UnlockStatus> getUnlockStatuses() {
        return unlockStatuses;
    }

    /**
     * @param unlockStatuses The unlockStatuses
     */
    public void setUnlockStatuses(List<UnlockStatus> unlockStatuses) {
        this.unlockStatuses = unlockStatuses;
    }

    /**
     * @return The requiredUnlockFlags
     */
    public List<Long> getRequiredUnlockFlags() {
        return requiredUnlockFlags;
    }

    /**
     * @param requiredUnlockFlags The requiredUnlockFlags
     */
    public void setRequiredUnlockFlags(List<Long> requiredUnlockFlags) {
        this.requiredUnlockFlags = requiredUnlockFlags;
    }

    /**
     * @return The vendorItemIndex
     */
    public Long getVendorItemIndex() {
        return vendorItemIndex;
    }

    /**
     * @param vendorItemIndex The vendorItemIndex
     */
    public void setVendorItemIndex(Long vendorItemIndex) {
        this.vendorItemIndex = vendorItemIndex;
    }

    /**
     * @return The costs
     */
    public List<Object> getCosts() {
        return costs;
    }

    /**
     * @param costs The costs
     */
    public void setCosts(List<Object> costs) {
        this.costs = costs;
    }

    /**
     * @return The item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item The item
     */
    public void setItem(Item item) {
        this.item = item;
    }
}

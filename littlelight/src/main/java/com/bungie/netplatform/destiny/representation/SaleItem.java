
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SaleItem {

    @SerializedName("item")
    @Expose
    public Item item;
    @SerializedName("vendorItemIndex")
    @Expose
    public long vendorItemIndex;
    @SerializedName("itemStatus")
    @Expose
    public long itemStatus;
    @SerializedName("costs")
    @Expose
    public List<Cost> costs = new ArrayList<Cost>();
    @SerializedName("requiredUnlockFlags")
    @Expose
    public List<Long> requiredUnlockFlags = new ArrayList<Long>();
    @SerializedName("unlockStatuses")
    @Expose
    public List<UnlockStatus> unlockStatuses = new ArrayList<UnlockStatus>();
    @SerializedName("failureIndexes")
    @Expose
    public List<Long> failureIndexes = new ArrayList<Long>();

}

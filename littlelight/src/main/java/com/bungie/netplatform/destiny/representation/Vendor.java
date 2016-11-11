
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Vendor {

    @SerializedName("vendorHash")
    @Expose
    public long vendorHash;
    @SerializedName("ackState")
    @Expose
    public AckState ackState;
    @SerializedName("nextRefreshDate")
    @Expose
    public String nextRefreshDate;
    @SerializedName("enabled")
    @Expose
    public boolean enabled;
    @SerializedName("saleItemCategories")
    @Expose
    public List<SaleItemCategory> saleItemCategories = new ArrayList<SaleItemCategory>();
    @SerializedName("inventoryBuckets")
    @Expose
    public List<Object> inventoryBuckets = new ArrayList<Object>();
    @SerializedName("progression")
    @Expose
    public Progression progression;
    @SerializedName("canPurchase")
    @Expose
    public boolean canPurchase;
    @SerializedName("currencies")
    @Expose
    public Currencies currencies;

}

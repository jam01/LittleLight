
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Trials {

    @SerializedName("highestWinRank")
    @Expose
    public long highestWinRank;
    @SerializedName("active")
    @Expose
    public boolean active;
    @SerializedName("scheduled")
    @Expose
    public boolean scheduled;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("ticket")
    @Expose
    public Ticket ticket;
    @SerializedName("winDetails")
    @Expose
    public List<Object> winDetails = new ArrayList<Object>();
    @SerializedName("buffs")
    @Expose
    public List<Object> buffs = new ArrayList<Object>();
    @SerializedName("currency")
    @Expose
    public Currency currency;
    @SerializedName("vendorHash")
    @Expose
    public long vendorHash;
    @SerializedName("playlistHash")
    @Expose
    public long playlistHash;
    @SerializedName("activityHashes")
    @Expose
    public List<Object> activityHashes = new ArrayList<Object>();

}


package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RaidActivities {

    @SerializedName("activityBundleHash")
    @Expose
    public long activityBundleHash;
    @SerializedName("raidIdentifier")
    @Expose
    public String raidIdentifier;
    @SerializedName("friendlyIdentifier")
    @Expose
    public String friendlyIdentifier;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("tiers")
    @Expose
    public List<Tier> tiers = new ArrayList<Tier>();
    @SerializedName("iconPath")
    @Expose
    public String iconPath;

}

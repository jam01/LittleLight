
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Nightfall {

    @SerializedName("activityBundleHash")
    @Expose
    public long activityBundleHash;
    @SerializedName("specificActivityHash")
    @Expose
    public long specificActivityHash;
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

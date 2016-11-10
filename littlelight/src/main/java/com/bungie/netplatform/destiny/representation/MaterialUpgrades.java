
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MaterialUpgrades {

    @SerializedName("activityBundleHash")
    @Expose
    public long activityBundleHash;
    @SerializedName("materialItemHash")
    @Expose
    public long materialItemHash;
    @SerializedName("itemSoidsUpgradable")
    @Expose
    public List<String> itemSoidsUpgradable = new ArrayList<String>();

}

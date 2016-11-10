
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DailyCrucible {

    @SerializedName("activityBundleHash")
    @Expose
    public long activityBundleHash;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("isCompleted")
    @Expose
    public boolean isCompleted;
    @SerializedName("activeRewardIndexes")
    @Expose
    public List<Long> activeRewardIndexes = new ArrayList<Long>();
    @SerializedName("iconPath")
    @Expose
    public String iconPath;
    @SerializedName("image")
    @Expose
    public String image;

}

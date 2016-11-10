
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DailyChapterActivities {

    @SerializedName("activityBundleHash")
    @Expose
    public long activityBundleHash;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("isCompleted")
    @Expose
    public boolean isCompleted;
    @SerializedName("isLocked")
    @Expose
    public boolean isLocked;
    @SerializedName("tierActivityHashes")
    @Expose
    public List<Long> tierActivityHashes = new ArrayList<Long>();
    @SerializedName("activeRewardIndexes")
    @Expose
    public ActiveRewardIndexes activeRewardIndexes;
    @SerializedName("iconPath")
    @Expose
    public String iconPath;

}

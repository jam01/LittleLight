
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Arena {

    @SerializedName("activityHash")
    @Expose
    public long activityHash;
    @SerializedName("iconPath")
    @Expose
    public String iconPath;
    @SerializedName("rounds")
    @Expose
    public List<Round> rounds = new ArrayList<Round>();
    @SerializedName("bossFight")
    @Expose
    public boolean bossFight;
    @SerializedName("bossSkulls")
    @Expose
    public List<Long> bossSkulls = new ArrayList<Long>();
    @SerializedName("activeRewardIndexes")
    @Expose
    public List<Long> activeRewardIndexes = new ArrayList<Long>();
    @SerializedName("isCompleted")
    @Expose
    public boolean isCompleted;

}

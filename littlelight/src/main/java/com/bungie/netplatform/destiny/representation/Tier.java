
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Tier {

    @SerializedName("activityHash")
    @Expose
    public long activityHash;
    @SerializedName("stepsComplete")
    @Expose
    public long stepsComplete;
    @SerializedName("stepsTotal")
    @Expose
    public long stepsTotal;
    @SerializedName("steps")
    @Expose
    public List<Step> steps = new ArrayList<Step>();
    @SerializedName("difficultyIdentifier")
    @Expose
    public String difficultyIdentifier;
    @SerializedName("activeRewardIndexes")
    @Expose
    public List<Long> activeRewardIndexes = new ArrayList<Long>();
    @SerializedName("skullIndexes")
    @Expose
    public List<Long> skullIndexes = new ArrayList<Long>();

    @SerializedName("specificActivityHash")
    @Expose
    public long specificActivityHash;
    @SerializedName("isCompleted")
    @Expose
    public boolean isCompleted;
    @SerializedName("isSuccessful")
    @Expose
    public boolean isSuccessful;
}

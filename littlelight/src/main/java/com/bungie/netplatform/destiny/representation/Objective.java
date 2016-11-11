
package com.bungie.netplatform.destiny.representation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Objective {

    @SerializedName("objectiveHash")
    @Expose
    public long objectiveHash;
    @SerializedName("destinationHash")
    @Expose
    public long destinationHash;
    @SerializedName("activityHash")
    @Expose
    public long activityHash;
    @SerializedName("progress")
    @Expose
    public long progress;
    @SerializedName("hasProgress")
    @Expose
    public boolean hasProgress;
    @SerializedName("isComplete")
    @Expose
    public boolean isComplete;

}

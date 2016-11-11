
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Quest {

    @SerializedName("questHash")
    @Expose
    public long questHash;
    @SerializedName("stepHash")
    @Expose
    public long stepHash;
    @SerializedName("stepObjectives")
    @Expose
    public List<StepObjective> stepObjectives = new ArrayList<StepObjective>();
    @SerializedName("tracked")
    @Expose
    public boolean tracked;
    @SerializedName("itemInstanceId")
    @Expose
    public String itemInstanceId;
    @SerializedName("completed")
    @Expose
    public boolean completed;
    @SerializedName("started")
    @Expose
    public boolean started;

}

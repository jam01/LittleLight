
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Node {

    @SerializedName("isActivated")
    @Expose
    public boolean isActivated;
    @SerializedName("stepIndex")
    @Expose
    public long stepIndex;
    @SerializedName("state")
    @Expose
    public long state;
    @SerializedName("hidden")
    @Expose
    public boolean hidden;
    @SerializedName("nodeHash")
    @Expose
    public long nodeHash;

}

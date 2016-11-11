
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AckState {

    @SerializedName("needsAck")
    @Expose
    public boolean needsAck;
    @SerializedName("ackId")
    @Expose
    public String ackId;

}

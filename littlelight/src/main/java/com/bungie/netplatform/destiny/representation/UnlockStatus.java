
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UnlockStatus {

    @SerializedName("unlockFlagHash")
    @Expose
    public long unlockFlagHash;
    @SerializedName("isSet")
    @Expose
    public boolean isSet;

}

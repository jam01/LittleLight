
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UnlockStatus {

    @SerializedName("unlockFlagHash")
    @Expose
    private Long unlockFlagHash;
    @SerializedName("isSet")
    @Expose
    private Boolean isSet;

    /**
     * @return The unlockFlagHash
     */
    public Long getUnlockFlagHash() {
        return unlockFlagHash;
    }

    /**
     * @param unlockFlagHash The unlockFlagHash
     */
    public void setUnlockFlagHash(Long unlockFlagHash) {
        this.unlockFlagHash = unlockFlagHash;
    }

    /**
     * @return The isSet
     */
    public Boolean getIsSet() {
        return isSet;
    }

    /**
     * @param isSet The isSet
     */
    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }
}

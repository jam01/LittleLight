
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Buff {

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("itemHash")
    @Expose
    private Long itemHash;
    @SerializedName("isUsed")
    @Expose
    private Boolean isUsed;

    /**
     * @return The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return The itemHash
     */
    public Long getItemHash() {
        return itemHash;
    }

    /**
     * @param itemHash The itemHash
     */
    public void setItemHash(Long itemHash) {
        this.itemHash = itemHash;
    }

    /**
     * @return The isUsed
     */
    public Boolean getIsUsed() {
        return isUsed;
    }

    /**
     * @param isUsed The isUsed
     */
    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}

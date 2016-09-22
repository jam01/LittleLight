
package com.bungie.netplatform.destiny.representation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Context {

    @SerializedName("isFollowing")
    @Expose
    private boolean isFollowing;
    @SerializedName("ignoreStatus")
    @Expose
    private IgnoreStatus ignoreStatus;

    /**
     * 
     * @return
     *     The isFollowing
     */
    public boolean isIsFollowing() {
        return isFollowing;
    }

    /**
     * 
     * @param isFollowing
     *     The isFollowing
     */
    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    /**
     * 
     * @return
     *     The ignoreStatus
     */
    public IgnoreStatus getIgnoreStatus() {
        return ignoreStatus;
    }

    /**
     * 
     * @param ignoreStatus
     *     The ignoreStatus
     */
    public void setIgnoreStatus(IgnoreStatus ignoreStatus) {
        this.ignoreStatus = ignoreStatus;
    }

}

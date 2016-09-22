
package com.bungie.netplatform.destiny.representation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class IgnoreStatus {

    @SerializedName("isIgnored")
    @Expose
    private boolean isIgnored;
    @SerializedName("ignoreFlags")
    @Expose
    private int ignoreFlags;

    /**
     * 
     * @return
     *     The isIgnored
     */
    public boolean isIsIgnored() {
        return isIgnored;
    }

    /**
     * 
     * @param isIgnored
     *     The isIgnored
     */
    public void setIsIgnored(boolean isIgnored) {
        this.isIgnored = isIgnored;
    }

    /**
     * 
     * @return
     *     The ignoreFlags
     */
    public int getIgnoreFlags() {
        return ignoreFlags;
    }

    /**
     * 
     * @param ignoreFlags
     *     The ignoreFlags
     */
    public void setIgnoreFlags(int ignoreFlags) {
        this.ignoreFlags = ignoreFlags;
    }

}

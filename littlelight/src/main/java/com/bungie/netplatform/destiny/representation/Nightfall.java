
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Nightfall {

    @SerializedName("tiers")
    @Expose
    private List<Tier> tiers = new ArrayList<Tier>();
    @SerializedName("activityBundleHash")
    @Expose
    private Long activityBundleHash;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;

    /**
     * @return The tiers
     */
    public List<Tier> getTiers() {
        return tiers;
    }

    /**
     * @param tiers The tiers
     */
    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }

    /**
     * @return The activityBundleHash
     */
    public Long getActivityBundleHash() {
        return activityBundleHash;
    }

    /**
     * @param activityBundleHash The activityBundleHash
     */
    public void setActivityBundleHash(Long activityBundleHash) {
        this.activityBundleHash = activityBundleHash;
    }

    /**
     * @return The iconPath
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * @param iconPath The iconPath
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * @return The expirationDate
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate The expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

}

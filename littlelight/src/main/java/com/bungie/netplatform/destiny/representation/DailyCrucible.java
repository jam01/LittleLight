
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DailyCrucible {

    @SerializedName("activityBundleHash")
    @Expose
    private Long activityBundleHash;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("activeRewardIndexes")
    @Expose
    private List<Long> activeRewardIndexes = new ArrayList<Long>();
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;

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

    /**
     * @return The activeRewardIndexes
     */
    public List<Long> getActiveRewardIndexes() {
        return activeRewardIndexes;
    }

    /**
     * @param activeRewardIndexes The activeRewardIndexes
     */
    public void setActiveRewardIndexes(List<Long> activeRewardIndexes) {
        this.activeRewardIndexes = activeRewardIndexes;
    }

    /**
     * @return The isCompleted
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * @param isCompleted The isCompleted
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}

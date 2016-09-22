
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DailyChapterActivities {

    @SerializedName("activityBundleHash")
    @Expose
    private Long activityBundleHash;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("tierActivityHashes")
    @Expose
    private List<Long> tierActivityHashes = new ArrayList<Long>();
    @SerializedName("activeRewardIndexes")
    @Expose
    private ActiveRewardIndexes activeRewardIndexes;
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;
    @SerializedName("isLocked")
    @Expose
    private Boolean isLocked;

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
     * @return The tierActivityHashes
     */
    public List<Long> getTierActivityHashes() {
        return tierActivityHashes;
    }

    /**
     * @param tierActivityHashes The tierActivityHashes
     */
    public void setTierActivityHashes(List<Long> tierActivityHashes) {
        this.tierActivityHashes = tierActivityHashes;
    }

    /**
     * @return The activeRewardIndexes
     */
    public ActiveRewardIndexes getActiveRewardIndexes() {
        return activeRewardIndexes;
    }

    /**
     * @param activeRewardIndexes The activeRewardIndexes
     */
    public void setActiveRewardIndexes(ActiveRewardIndexes activeRewardIndexes) {
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

    /**
     * @return The isLocked
     */
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * @param isLocked The isLocked
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

}

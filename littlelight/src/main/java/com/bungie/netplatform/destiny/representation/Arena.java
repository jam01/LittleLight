
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Arena {

    @SerializedName("bossSkulls")
    @Expose
    private List<Long> bossSkulls = new ArrayList<Long>();
    @SerializedName("bossFight")
    @Expose
    private Boolean bossFight;
    @SerializedName("activityHash")
    @Expose
    private Long activityHash;
    @SerializedName("rounds")
    @Expose
    private List<Round> rounds = new ArrayList<Round>();
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("activeRewardIndexes")
    @Expose
    private List<Long> activeRewardIndexes = new ArrayList<Long>();
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;

    /**
     * @return The bossSkulls
     */
    public List<Long> getBossSkulls() {
        return bossSkulls;
    }

    /**
     * @param bossSkulls The bossSkulls
     */
    public void setBossSkulls(List<Long> bossSkulls) {
        this.bossSkulls = bossSkulls;
    }

    /**
     * @return The bossFight
     */
    public Boolean getBossFight() {
        return bossFight;
    }

    /**
     * @param bossFight The bossFight
     */
    public void setBossFight(Boolean bossFight) {
        this.bossFight = bossFight;
    }

    /**
     * @return The activityHash
     */
    public Long getActivityHash() {
        return activityHash;
    }

    /**
     * @param activityHash The activityHash
     */
    public void setActivityHash(Long activityHash) {
        this.activityHash = activityHash;
    }

    /**
     * @return The rounds
     */
    public List<Round> getRounds() {
        return rounds;
    }

    /**
     * @param rounds The rounds
     */
    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
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

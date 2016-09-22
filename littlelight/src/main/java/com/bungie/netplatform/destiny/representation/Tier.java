
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Tier {
    @SerializedName("isSuccessful")
    @Expose
    private Boolean isSuccessful;

    @SerializedName("activeRewardIndexes")
    @Expose
    private List<Long> activeRewardIndexes = new ArrayList<Long>();

    @SerializedName("activityHash")
    @Expose
    private Long activityHash;

    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;

    @SerializedName("difficultyIdentifier")
    @Expose
    private String difficultyIdentifier;

    @SerializedName("stepsComplete")
    @Expose
    private Long stepsComplete;

    @SerializedName("stepsTotal")
    @Expose
    private Long stepsTotal;

    /**
     * @return The isSuccessful
     */
    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    /**
     * @param isSuccessful The isSuccessful
     */
    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
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
     * @return The difficultyIdentifier
     */
    public String getDifficultyIdentifier() {
        return difficultyIdentifier;
    }

    /**
     * @param difficultyIdentifier The difficultyIdentifier
     */
    public void setDifficultyIdentifier(String difficultyIdentifier) {
        this.difficultyIdentifier = difficultyIdentifier;
    }

    /**
     * @return The stepsTotal
     */
    public Long getStepsTotal() {
        return stepsTotal;
    }

    /**
     * @param stepsTotal The stepsTotal
     */
    public void setStepsTotal(Long stepsTotal) {
        this.stepsTotal = stepsTotal;
    }

    /**
     * @return The stepsComplete
     */
    public Long getStepsComplete() {
        return stepsComplete;
    }

    /**
     * @param stepsComplete The stepsComplete
     */
    public void setStepsComplete(Long stepsComplete) {
        this.stepsComplete = stepsComplete;
    }
}

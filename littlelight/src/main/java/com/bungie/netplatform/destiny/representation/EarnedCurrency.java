
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EarnedCurrency {

    @SerializedName("dailyProgress")
    @Expose
    private Long dailyProgress;
    @SerializedName("currentProgress")
    @Expose
    private Long currentProgress;
    @SerializedName("level")
    @Expose
    private Long level;
    @SerializedName("progressToNextLevel")
    @Expose
    private Long progressToNextLevel;
    @SerializedName("weeklyProgress")
    @Expose
    private Long weeklyProgress;
    @SerializedName("progressionHash")
    @Expose
    private Long progressionHash;
    @SerializedName("step")
    @Expose
    private Long step;
    @SerializedName("nextLevelAt")
    @Expose
    private Long nextLevelAt;

    /**
     * @return The dailyProgress
     */
    public Long getDailyProgress() {
        return dailyProgress;
    }

    /**
     * @param dailyProgress The dailyProgress
     */
    public void setDailyProgress(Long dailyProgress) {
        this.dailyProgress = dailyProgress;
    }

    /**
     * @return The currentProgress
     */
    public Long getCurrentProgress() {
        return currentProgress;
    }

    /**
     * @param currentProgress The currentProgress
     */
    public void setCurrentProgress(Long currentProgress) {
        this.currentProgress = currentProgress;
    }

    /**
     * @return The level
     */
    public Long getLevel() {
        return level;
    }

    /**
     * @param level The level
     */
    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * @return The progressToNextLevel
     */
    public Long getProgressToNextLevel() {
        return progressToNextLevel;
    }

    /**
     * @param progressToNextLevel The progressToNextLevel
     */
    public void setProgressToNextLevel(Long progressToNextLevel) {
        this.progressToNextLevel = progressToNextLevel;
    }

    /**
     * @return The weeklyProgress
     */
    public Long getWeeklyProgress() {
        return weeklyProgress;
    }

    /**
     * @param weeklyProgress The weeklyProgress
     */
    public void setWeeklyProgress(Long weeklyProgress) {
        this.weeklyProgress = weeklyProgress;
    }

    /**
     * @return The progressionHash
     */
    public Long getProgressionHash() {
        return progressionHash;
    }

    /**
     * @param progressionHash The progressionHash
     */
    public void setProgressionHash(Long progressionHash) {
        this.progressionHash = progressionHash;
    }

    /**
     * @return The step
     */
    public Long getStep() {
        return step;
    }

    /**
     * @param step The step
     */
    public void setStep(Long step) {
        this.step = step;
    }

    /**
     * @return The nextLevelAt
     */
    public Long getNextLevelAt() {
        return nextLevelAt;
    }

    /**
     * @param nextLevelAt The nextLevelAt
     */
    public void setNextLevelAt(Long nextLevelAt) {
        this.nextLevelAt = nextLevelAt;
    }
}

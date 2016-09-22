package com.bungie.netplatform.destiny.representation;

/**
 * Created by jam01 on 5/27/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
public class ActivityAdvisors {
    @SerializedName("nightfall")
    @Expose
    private Nightfall nightfall;
    @SerializedName("activityBundleHash")
    @Expose
    private Long activityBundleHash;


    @SerializedName("dailyCrucible")
    @Expose
    private DailyCrucible dailyCrucible;


    @SerializedName("materialUpgrades")
    @Expose
    private com.bungie.netplatform.destiny.representation.MaterialUpgrades materialUpgrades;


    @SerializedName("dailyChapterActivities")
    @Expose
    private DailyChapterActivities dailyChapterActivities;


    @SerializedName("raidActivities")
    @Expose
    private com.bungie.netplatform.destiny.representation.RaidActivities raidActivities;

    @SerializedName("heroicStrike")
    @Expose
    private com.bungie.netplatform.destiny.representation.HeroicStrike heroicStrike;


    /**
     * @return The nightfall
     */
    public Nightfall getNightfall() {
        return nightfall;
    }

    /**
     * @param nightfall The nightfall
     */
    public void setNightfall(Nightfall nightfall) {
        this.nightfall = nightfall;
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
     * @return The dailyCrucible
     */
    public DailyCrucible getDailyCrucible() {
        return dailyCrucible;
    }

    /**
     * @param dailyCrucible The dailyCrucible
     */
    public void setDailyCrucible(DailyCrucible dailyCrucible) {
        this.dailyCrucible = dailyCrucible;
    }


    /**
     * @return The materialUpgrades
     */
    public com.bungie.netplatform.destiny.representation.MaterialUpgrades getMaterialUpgrades() {
        return materialUpgrades;
    }

    /**
     * @param materialUpgrades The materialUpgrades
     */
    public void setMaterialUpgrades(com.bungie.netplatform.destiny.representation.MaterialUpgrades materialUpgrades) {
        this.materialUpgrades = materialUpgrades;
    }


    /**
     * @return The dailyChapterActivities
     */
    public DailyChapterActivities getDailyChapterActivities() {
        return dailyChapterActivities;
    }

    /**
     * @param dailyChapterActivities The dailyChapterActivities
     */
    public void setDailyChapterActivities(DailyChapterActivities dailyChapterActivities) {
        this.dailyChapterActivities = dailyChapterActivities;
    }


    /**
     * @return The raidActivities
     */
    public com.bungie.netplatform.destiny.representation.RaidActivities getRaidActivities() {
        return raidActivities;
    }

    /**
     * @param raidActivities The raidActivities
     */
    public void setRaidActivities(com.bungie.netplatform.destiny.representation.RaidActivities raidActivities) {
        this.raidActivities = raidActivities;
    }


    /**
     * @return The heroicStrike
     */
    public com.bungie.netplatform.destiny.representation.HeroicStrike getHeroicStrike() {
        return heroicStrike;
    }

    /**
     * @param heroicStrike The heroicStrike
     */
    public void setHeroicStrike(com.bungie.netplatform.destiny.representation.HeroicStrike heroicStrike) {
        this.heroicStrike = heroicStrike;
    }

}

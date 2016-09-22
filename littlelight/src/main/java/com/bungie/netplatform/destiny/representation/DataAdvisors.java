
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataAdvisors {

    @SerializedName("bonuses")
    @Expose
    private List<Bonuse> bonuses = new ArrayList<Bonuse>();
    @SerializedName("arena")
    @Expose
    private List<Arena> arena = new ArrayList<Arena>();
    @SerializedName("earnedCurrency")
    @Expose
    private List<com.bungie.netplatform.destiny.representation.EarnedCurrency> earnedCurrency = new ArrayList<com.bungie.netplatform.destiny.representation.EarnedCurrency>();
    @SerializedName("factions")
    @Expose
    private com.bungie.netplatform.destiny.representation.Factions factions;
    @SerializedName("events")
    @Expose
    private List<Event> events = new ArrayList<Event>();
    @SerializedName("vendorAdvisors")
    @Expose
    private VendorAdvisors vendorAdvisors;
    @SerializedName("areOffersAvailable")
    @Expose
    private Boolean areOffersAvailable;
    @SerializedName("activityAdvisors")
    @Expose
    private List<ActivityAdvisors> activityAdvisors;
    @SerializedName("trials")
    @Expose
    private com.bungie.netplatform.destiny.representation.Trials trials;

    /**
     * @return The bonuses
     */
    public List<Bonuse> getBonuses() {
        return bonuses;
    }

    /**
     * @param bonuses The bonuses
     */
    public void setBonuses(List<Bonuse> bonuses) {
        this.bonuses = bonuses;
    }

    /**
     * @return The arena
     */
    public List<Arena> getArena() {
        return arena;
    }

    /**
     * @param arena The arena
     */
    public void setArena(List<Arena> arena) {
        this.arena = arena;
    }

    /**
     * @return The earnedCurrency
     */
    public List<com.bungie.netplatform.destiny.representation.EarnedCurrency> getEarnedCurrency() {
        return earnedCurrency;
    }

    /**
     * @param earnedCurrency The earnedCurrency
     */
    public void setEarnedCurrency(List<com.bungie.netplatform.destiny.representation.EarnedCurrency> earnedCurrency) {
        this.earnedCurrency = earnedCurrency;
    }

    /**
     * @return The factions
     */
    public com.bungie.netplatform.destiny.representation.Factions getFactions() {
        return factions;
    }

    /**
     * @param factions The factions
     */
    public void setFactions(com.bungie.netplatform.destiny.representation.Factions factions) {
        this.factions = factions;
    }

    /**
     * @return The events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events The events
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return The vendorAdvisors
     */
    public VendorAdvisors getVendorAdvisors() {
        return vendorAdvisors;
    }

    /**
     * @param vendorAdvisors The vendorAdvisors
     */
    public void setVendorAdvisors(VendorAdvisors vendorAdvisors) {
        this.vendorAdvisors = vendorAdvisors;
    }

    /**
     * @return The areOffersAvailable
     */
    public Boolean getAreOffersAvailable() {
        return areOffersAvailable;
    }

    /**
     * @param areOffersAvailable The areOffersAvailable
     */
    public void setAreOffersAvailable(Boolean areOffersAvailable) {
        this.areOffersAvailable = areOffersAvailable;
    }

    /**
     * @return The activityAdvisors
     */
    public List<ActivityAdvisors> getActivityAdvisors() {
        return activityAdvisors;
    }

    /**
     * @param activityAdvisorsOLD The activityAdvisors
     */
    public void setActivityAdvisorsOLD(List<ActivityAdvisors> activityAdvisors) {
        this.activityAdvisors = activityAdvisors;
    }

    /**
     * @return The trials
     */
    public com.bungie.netplatform.destiny.representation.Trials getTrials() {
        return trials;
    }

    /**
     * @param trials The trials
     */
    public void setTrials(com.bungie.netplatform.destiny.representation.Trials trials) {
        this.trials = trials;
    }

}

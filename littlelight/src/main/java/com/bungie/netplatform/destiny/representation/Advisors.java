
package com.bungie.netplatform.destiny.representation;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Advisors {

    @SerializedName("nextWeeklyReset")
    @Expose
    public String nextWeeklyReset;
    @SerializedName("nextDailyReset")
    @Expose
    public String nextDailyReset;
    @SerializedName("previousWeeklyReset")
    @Expose
    public String previousWeeklyReset;
    @SerializedName("previousDailyReset")
    @Expose
    public String previousDailyReset;
    @SerializedName("activityAdvisors")
    @Expose
    public JsonObject activityAdvisors;
//    public ActivityAdvisors activityAdvisors;
    @SerializedName("itemQuantities")
    @Expose
    public ItemQuantities itemQuantities;
    @SerializedName("areOffersAvailable")
    @Expose
    public boolean areOffersAvailable;
    @SerializedName("events")
    @Expose
    public List<Event> events = new ArrayList<Event>();
    @SerializedName("bonuses")
    @Expose
    public List<Object> bonuses = new ArrayList<Object>();
//    @SerializedName("factions")
//    @Expose
//    public Factions factions;
    @SerializedName("arena")
    @Expose
    public List<Arena> arena = new ArrayList<Arena>();
    @SerializedName("elderChallenge")
    @Expose
    public ElderChallenge elderChallenge;
    @SerializedName("trials")
    @Expose
    public Trials trials;
    @SerializedName("quests")
    @Expose
    public Quests_ quests;

}

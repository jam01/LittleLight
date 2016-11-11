
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ElderChallenge {

    @SerializedName("playlistActivityHash")
    @Expose
    public long playlistActivityHash;
    @SerializedName("activityHash")
    @Expose
    public long activityHash;
    @SerializedName("iconPath")
    @Expose
    public String iconPath;
    @SerializedName("rounds")
    @Expose
    public List<Round> rounds = new ArrayList<Round>();
    @SerializedName("objectives")
    @Expose
    public List<Objective> objectives = new ArrayList<Objective>();
    @SerializedName("hasTicket")
    @Expose
    public boolean hasTicket;
//    @SerializedName("bounties")
//    @Expose
//    public Bounties_ bounties;
    @SerializedName("playlistSkullIndexes")
    @Expose
    public List<Long> playlistSkullIndexes = new ArrayList<Long>();
    @SerializedName("playlistBonusSkullIndexes")
    @Expose
    public List<Long> playlistBonusSkullIndexes = new ArrayList<Long>();

}

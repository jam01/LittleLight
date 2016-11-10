
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Round {

    @SerializedName("enemyRaceHash")
    @Expose
    public long enemyRaceHash;
    @SerializedName("skulls")
    @Expose
    public List<Long> skulls = new ArrayList<Long>();

    @SerializedName("bossCombatantHash")
    @Expose
    public long bossCombatantHash;
    @SerializedName("bossLightLevel")
    @Expose
    public long bossLightLevel;
}

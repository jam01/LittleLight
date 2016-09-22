
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Round {

    @SerializedName("skulls")
    @Expose
    private List<Long> skulls = new ArrayList<Long>();
    @SerializedName("enemyRaceHash")
    @Expose
    private Long enemyRaceHash;

    /**
     * @return The skulls
     */
    public List<Long> getSkulls() {
        return skulls;
    }

    /**
     * @param skulls The skulls
     */
    public void setSkulls(List<Long> skulls) {
        this.skulls = skulls;
    }

    /**
     * @return The enemyRaceHash
     */
    public Long getEnemyRaceHash() {
        return enemyRaceHash;
    }

    /**
     * @param enemyRaceHash The enemyRaceHash
     */
    public void setEnemyRaceHash(Long enemyRaceHash) {
        this.enemyRaceHash = enemyRaceHash;
    }
}

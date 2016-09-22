
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class BonusRank {

    @SerializedName("statId")
    @Expose
    private Long statId;
    @SerializedName("rank")
    @Expose
    private Long rank;

    /**
     * @return The statId
     */
    public Long getStatId() {
        return statId;
    }

    /**
     * @param statId The statId
     */
    public void setStatId(Long statId) {
        this.statId = statId;
    }

    /**
     * @return The rank
     */
    public Long getRank() {
        return rank;
    }

    /**
     * @param rank The rank
     */
    public void setRank(Long rank) {
        this.rank = rank;
    }
}

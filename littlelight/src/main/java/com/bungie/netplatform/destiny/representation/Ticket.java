
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Ticket {

    @SerializedName("maxWins")
    @Expose
    private Long maxWins;
    @SerializedName("losses")
    @Expose
    private Long losses;
    @SerializedName("maxLosses")
    @Expose
    private Long maxLosses;
    @SerializedName("wins")
    @Expose
    private Long wins;
    @SerializedName("hasTicket")
    @Expose
    private Boolean hasTicket;

    /**
     * @return The maxWins
     */
    public Long getMaxWins() {
        return maxWins;
    }

    /**
     * @param maxWins The maxWins
     */
    public void setMaxWins(Long maxWins) {
        this.maxWins = maxWins;
    }

    /**
     * @return The losses
     */
    public Long getLosses() {
        return losses;
    }

    /**
     * @param losses The losses
     */
    public void setLosses(Long losses) {
        this.losses = losses;
    }

    /**
     * @return The maxLosses
     */
    public Long getMaxLosses() {
        return maxLosses;
    }

    /**
     * @param maxLosses The maxLosses
     */
    public void setMaxLosses(Long maxLosses) {
        this.maxLosses = maxLosses;
    }

    /**
     * @return The wins
     */
    public Long getWins() {
        return wins;
    }

    /**
     * @param wins The wins
     */
    public void setWins(Long wins) {
        this.wins = wins;
    }

    /**
     * @return The hasTicket
     */
    public Boolean getHasTicket() {
        return hasTicket;
    }

    /**
     * @param hasTicket The hasTicket
     */
    public void setHasTicket(Boolean hasTicket) {
        this.hasTicket = hasTicket;
    }

}

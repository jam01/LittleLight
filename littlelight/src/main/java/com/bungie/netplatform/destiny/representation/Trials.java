
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Trials {

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("buffs")
    @Expose
    private List<Buff> buffs = new ArrayList<Buff>();
    @SerializedName("scheduled")
    @Expose
    private Boolean scheduled;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("winDetails")
    @Expose
    private List<Object> winDetails = new ArrayList<Object>();
    @SerializedName("highestWinRank")
    @Expose
    private Long highestWinRank;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("currency")
    @Expose
    private Currency currency;

    /**
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The buffs
     */
    public List<Buff> getBuffs() {
        return buffs;
    }

    /**
     * @param buffs The buffs
     */
    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    /**
     * @return The scheduled
     */
    public Boolean getScheduled() {
        return scheduled;
    }

    /**
     * @param scheduled The scheduled
     */
    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * @return The expirationDate
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate The expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return The ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @param ticket The ticket
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return The winDetails
     */
    public List<Object> getWinDetails() {
        return winDetails;
    }

    /**
     * @param winDetails The winDetails
     */
    public void setWinDetails(List<Object> winDetails) {
        this.winDetails = winDetails;
    }

    /**
     * @return The highestWinRank
     */
    public Long getHighestWinRank() {
        return highestWinRank;
    }

    /**
     * @param highestWinRank The highestWinRank
     */
    public void setHighestWinRank(Long highestWinRank) {
        this.highestWinRank = highestWinRank;
    }

    /**
     * @return The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return The currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

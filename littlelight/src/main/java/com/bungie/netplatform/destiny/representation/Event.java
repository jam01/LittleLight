
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Event {

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("eventHash")
    @Expose
    private Long eventHash;
    @SerializedName("expirationKnown")
    @Expose
    private Boolean expirationKnown;
    @SerializedName("eventIdentifier")
    @Expose
    private String eventIdentifier;

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
     * @return The eventHash
     */
    public Long getEventHash() {
        return eventHash;
    }

    /**
     * @param eventHash The eventHash
     */
    public void setEventHash(Long eventHash) {
        this.eventHash = eventHash;
    }

    /**
     * @return The expirationKnown
     */
    public Boolean getExpirationKnown() {
        return expirationKnown;
    }

    /**
     * @param expirationKnown The expirationKnown
     */
    public void setExpirationKnown(Boolean expirationKnown) {
        this.expirationKnown = expirationKnown;
    }

    /**
     * @return The eventIdentifier
     */
    public String getEventIdentifier() {
        return eventIdentifier;
    }

    /**
     * @param eventIdentifier The eventIdentifier
     */
    public void setEventIdentifier(String eventIdentifier) {
        this.eventIdentifier = eventIdentifier;
    }
}

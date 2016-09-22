package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Available {

    @Expose
    private Long activityHash;
    @Expose
    private Boolean isVisible;
    @Expose
    private Boolean canLead;
    @Expose
    private Boolean isCompleted;
    @Expose
    private Boolean isNew;
    @Expose
    private Boolean canJoin;

    /**
     * @return The activityHash
     */
    public Long getActivityHash() {
        return activityHash;
    }

    /**
     * @param activityHash The activityHash
     */
    public void setActivityHash(Long activityHash) {
        this.activityHash = activityHash;
    }

    /**
     * @return The isVisible
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * @param isVisible The isVisible
     */
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * @return The canLead
     */
    public Boolean getCanLead() {
        return canLead;
    }

    /**
     * @param canLead The canLead
     */
    public void setCanLead(Boolean canLead) {
        this.canLead = canLead;
    }

    /**
     * @return The isCompleted
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * @param isCompleted The isCompleted
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * @return The isNew
     */
    public Boolean getIsNew() {
        return isNew;
    }

    /**
     * @param isNew The isNew
     */
    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * @return The canJoin
     */
    public Boolean getCanJoin() {
        return canJoin;
    }

    /**
     * @param canJoin The canJoin
     */
    public void setCanJoin(Boolean canJoin) {
        this.canJoin = canJoin;
    }
}

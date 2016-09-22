
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Node {

    @SerializedName("state")
    @Expose
    private Long state;
    @SerializedName("isActivated")
    @Expose
    private Boolean isActivated;
    @SerializedName("nodeHash")
    @Expose
    private Long nodeHash;
    @SerializedName("hidden")
    @Expose
    private Boolean hidden;
    @SerializedName("stepIndex")
    @Expose
    private Long stepIndex;

    /**
     *
     * @return
     *     The state
     */
    public Long getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(Long state) {
        this.state = state;
    }

    /**
     * @return The isActivated
     */
    public Boolean getIsActivated() {
        return isActivated;
    }

    /**
     * @param isActivated The isActivated
     */
    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    /**
     * @return The nodeHash
     */
    public Long getNodeHash() {
        return nodeHash;
    }

    /**
     *
     * @param nodeHash
     *     The nodeHash
     */
    public void setNodeHash(Long nodeHash) {
        this.nodeHash = nodeHash;
    }

    /**
     *
     * @return
     *     The hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     *
     * @param hidden
     *     The hidden
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return The stepIndex
     */
    public Long getStepIndex() {
        return stepIndex;
    }

    /**
     *
     * @param stepIndex
     *     The stepIndex
     */
    public void setStepIndex(Long stepIndex) {
        this.stepIndex = stepIndex;
    }
}

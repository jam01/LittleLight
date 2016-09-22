package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class STAT_DEFENSE {

    @Expose
    private Long value;
    @Expose
    private Long maximumValue;
    @Expose
    private Long statHash;

    /**
     * @return The value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * @return The maximumValue
     */
    public Long getMaximumValue() {
        return maximumValue;
    }

    /**
     * @param maximumValue The maximumValue
     */
    public void setMaximumValue(Long maximumValue) {
        this.maximumValue = maximumValue;
    }

    /**
     * @return The statHash
     */
    public Long getStatHash() {
        return statHash;
    }

    /**
     * @param statHash The statHash
     */
    public void setStatHash(Long statHash) {
        this.statHash = statHash;
    }

}

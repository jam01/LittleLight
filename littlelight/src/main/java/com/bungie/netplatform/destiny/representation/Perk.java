package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;


import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Perk {

    @Expose
    private Boolean isActive;
    @Expose
    private Long perkHash;
    @Expose
    private String iconPath;

    /**
     * @return The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return The perkHash
     */
    public Long getPerkHash() {
        return perkHash;
    }

    /**
     * @param perkHash The perkHash
     */
    public void setPerkHash(Long perkHash) {
        this.perkHash = perkHash;
    }

    /**
     * @return The iconPath
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * @param iconPath The iconPath
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}

package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EquippingBlock {

    @Expose
    private Long weaponSandboxPatternIndex;
    @Expose
    private Long gearArtArrangementIndex;
    @Expose
    private List<Object> defaultDyes = new ArrayList<Object>();
    @Expose
    private List<LockedDye> lockedDyes = new ArrayList<LockedDye>();
    @Expose
    private List<Object> customDyes = new ArrayList<Object>();
    @Expose
    private com.bungie.netplatform.destiny.representation.CustomDyeExpression customDyeExpression;
    @Expose
    private Long weaponPatternHash;

    /**
     * @return The weaponSandboxPatternIndex
     */
    public Long getWeaponSandboxPatternIndex() {
        return weaponSandboxPatternIndex;
    }

    /**
     * @param weaponSandboxPatternIndex The weaponSandboxPatternIndex
     */
    public void setWeaponSandboxPatternIndex(Long weaponSandboxPatternIndex) {
        this.weaponSandboxPatternIndex = weaponSandboxPatternIndex;
    }

    /**
     * @return The gearArtArrangementIndex
     */
    public Long getGearArtArrangementIndex() {
        return gearArtArrangementIndex;
    }

    /**
     * @param gearArtArrangementIndex The gearArtArrangementIndex
     */
    public void setGearArtArrangementIndex(Long gearArtArrangementIndex) {
        this.gearArtArrangementIndex = gearArtArrangementIndex;
    }

    /**
     * @return The defaultDyes
     */
    public List<Object> getDefaultDyes() {
        return defaultDyes;
    }

    /**
     * @param defaultDyes The defaultDyes
     */
    public void setDefaultDyes(List<Object> defaultDyes) {
        this.defaultDyes = defaultDyes;
    }

    /**
     * @return The lockedDyes
     */
    public List<LockedDye> getLockedDyes() {
        return lockedDyes;
    }

    /**
     * @param lockedDyes The lockedDyes
     */
    public void setLockedDyes(List<LockedDye> lockedDyes) {
        this.lockedDyes = lockedDyes;
    }

    /**
     * @return The customDyes
     */
    public List<Object> getCustomDyes() {
        return customDyes;
    }

    /**
     * @param customDyes The customDyes
     */
    public void setCustomDyes(List<Object> customDyes) {
        this.customDyes = customDyes;
    }

    /**
     * @return The customDyeExpression
     */
    public com.bungie.netplatform.destiny.representation.CustomDyeExpression getCustomDyeExpression() {
        return customDyeExpression;
    }

    /**
     * @param customDyeExpression The customDyeExpression
     */
    public void setCustomDyeExpression(com.bungie.netplatform.destiny.representation.CustomDyeExpression customDyeExpression) {
        this.customDyeExpression = customDyeExpression;
    }

    /**
     * @return The weaponPatternHash
     */
    public Long getWeaponPatternHash() {
        return weaponPatternHash;
    }

    /**
     * @param weaponPatternHash The weaponPatternHash
     */
    public void setWeaponPatternHash(Long weaponPatternHash) {
        this.weaponPatternHash = weaponPatternHash;
    }
}

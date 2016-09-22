package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CharacterComplete {

    @Expose
    private Progressions progressions;
    @Expose
    private List<CustomDye> customDyes = new ArrayList<CustomDye>();
    @Expose
    private List<Long> visibleBookHashes = new ArrayList<Long>();
    @Expose
    private Inventory inventory;
    @Expose
    private Long characterLevel;
    @Expose
    private Long defaultBookHash;
    @Expose
    private com.bungie.netplatform.destiny.representation.DirectorNodeStates directorNodeStates;
    @Expose
    private com.bungie.netplatform.destiny.representation.Activities activities;
    @Expose
    private com.bungie.netplatform.destiny.representation.CharacterBase characterBase;

    /**
     * @return The progressions
     */
    public Progressions getProgressions() {
        return progressions;
    }

    /**
     * @param progressions The progressions
     */
    public void setProgressions(Progressions progressions) {
        this.progressions = progressions;
    }

    /**
     * @return The customDyes
     */
    public List<CustomDye> getCustomDyes() {
        return customDyes;
    }

    /**
     * @param customDyes The customDyes
     */
    public void setCustomDyes(List<CustomDye> customDyes) {
        this.customDyes = customDyes;
    }

    /**
     * @return The visibleBookHashes
     */
    public List<Long> getVisibleBookHashes() {
        return visibleBookHashes;
    }

    /**
     * @param visibleBookHashes The visibleBookHashes
     */
    public void setVisibleBookHashes(List<Long> visibleBookHashes) {
        this.visibleBookHashes = visibleBookHashes;
    }

    /**
     * @return The inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param inventory The inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * @return The characterLevel
     */
    public Long getCharacterLevel() {
        return characterLevel;
    }

    /**
     * @param characterLevel The characterLevel
     */
    public void setCharacterLevel(Long characterLevel) {
        this.characterLevel = characterLevel;
    }

    /**
     * @return The defaultBookHash
     */
    public Long getDefaultBookHash() {
        return defaultBookHash;
    }

    /**
     * @param defaultBookHash The defaultBookHash
     */
    public void setDefaultBookHash(Long defaultBookHash) {
        this.defaultBookHash = defaultBookHash;
    }

    /**
     * @return The directorNodeStates
     */
    public com.bungie.netplatform.destiny.representation.DirectorNodeStates getDirectorNodeStates() {
        return directorNodeStates;
    }

    /**
     * @param directorNodeStates The directorNodeStates
     */
    public void setDirectorNodeStates(com.bungie.netplatform.destiny.representation.DirectorNodeStates directorNodeStates) {
        this.directorNodeStates = directorNodeStates;
    }

    /**
     * @return The activities
     */
    public com.bungie.netplatform.destiny.representation.Activities getActivities() {
        return activities;
    }

    /**
     * @param activities The activities
     */
    public void setActivities(com.bungie.netplatform.destiny.representation.Activities activities) {
        this.activities = activities;
    }

    /**
     * @return The characterBase
     */
    public com.bungie.netplatform.destiny.representation.CharacterBase getCharacterBase() {
        return characterBase;
    }

    /**
     * @param characterBase The characterBase
     */
    public void setCharacterBase(com.bungie.netplatform.destiny.representation.CharacterBase characterBase) {
        this.characterBase = characterBase;
    }
}

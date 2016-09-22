package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Progressions {

    @Expose
    private Long factionProgressionHash;
    @Expose
    private Long baseCharacterLevel;
    @Expose
    private List<Progression> progressions = new ArrayList<Progression>();
    @Expose
    private Boolean isPrestigeLevel;
    @Expose
    private LevelProgression levelProgression;
    @Expose
    private Float percentToNextLevel;

    /**
     * @return The factionProgressionHash
     */
    public Long getFactionProgressionHash() {
        return factionProgressionHash;
    }

    /**
     * @param factionProgressionHash The factionProgressionHash
     */
    public void setFactionProgressionHash(Long factionProgressionHash) {
        this.factionProgressionHash = factionProgressionHash;
    }

    /**
     * @return The baseCharacterLevel
     */
    public Long getBaseCharacterLevel() {
        return baseCharacterLevel;
    }

    /**
     * @param baseCharacterLevel The baseCharacterLevel
     */
    public void setBaseCharacterLevel(Long baseCharacterLevel) {
        this.baseCharacterLevel = baseCharacterLevel;
    }

    /**
     * @return The progressions
     */
    public List<Progression> getProgressions() {
        return progressions;
    }

    /**
     * @param progressions The progressions
     */
    public void setProgressions(List<Progression> progressions) {
        this.progressions = progressions;
    }

    /**
     * @return The isPrestigeLevel
     */
    public Boolean getIsPrestigeLevel() {
        return isPrestigeLevel;
    }

    /**
     * @param isPrestigeLevel The isPrestigeLevel
     */
    public void setIsPrestigeLevel(Boolean isPrestigeLevel) {
        this.isPrestigeLevel = isPrestigeLevel;
    }

    /**
     * @return The levelProgression
     */
    public LevelProgression getLevelProgression() {
        return levelProgression;
    }

    /**
     * @param levelProgression The levelProgression
     */
    public void setLevelProgression(LevelProgression levelProgression) {
        this.levelProgression = levelProgression;
    }

    /**
     * @return The percentToNextLevel
     */
    public Float getPercentToNextLevel() {
        return percentToNextLevel;
    }

    /**
     * @param percentToNextLevel The percentToNextLevel
     */
    public void setPercentToNextLevel(Float percentToNextLevel) {
        this.percentToNextLevel = percentToNextLevel;
    }
}

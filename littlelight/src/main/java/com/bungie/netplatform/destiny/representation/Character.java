package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Character {

    @Expose
    private CharacterBase characterBase;
    @Expose
    private com.bungie.netplatform.destiny.representation.LevelProgression levelProgression;
    @Expose
    private String emblemPath;
    @Expose
    private String backgroundPath;
    @Expose
    private Long emblemHash;
    @Expose
    private Long characterLevel;
    @Expose
    private Long baseCharacterLevel;
    @Expose
    private Boolean isPrestigeLevel;
    @Expose
    private Float percentToNextLevel;

    /**
     * @return The characterBase
     */
    public CharacterBase getCharacterBase() {
        return characterBase;
    }

    /**
     * @param characterBase The characterBase
     */
    public void setCharacterBase(CharacterBase characterBase) {
        this.characterBase = characterBase;
    }

    /**
     * @return The levelProgression
     */
    public com.bungie.netplatform.destiny.representation.LevelProgression getLevelProgression() {
        return levelProgression;
    }

    /**
     * @param levelProgression The levelProgression
     */
    public void setLevelProgression(com.bungie.netplatform.destiny.representation.LevelProgression levelProgression) {
        this.levelProgression = levelProgression;
    }

    /**
     * @return The emblemPath
     */
    public String getEmblemPath() {
        return emblemPath;
    }

    /**
     * @param emblemPath The emblemPath
     */
    public void setEmblemPath(String emblemPath) {
        this.emblemPath = emblemPath;
    }

    /**
     * @return The backgroundPath
     */
    public String getBackgroundPath() {
        return backgroundPath;
    }

    /**
     * @param backgroundPath The backgroundPath
     */
    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    /**
     * @return The emblemHash
     */
    public Long getEmblemHash() {
        return emblemHash;
    }

    /**
     * @param emblemHash The emblemHash
     */
    public void setEmblemHash(Long emblemHash) {
        this.emblemHash = emblemHash;
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

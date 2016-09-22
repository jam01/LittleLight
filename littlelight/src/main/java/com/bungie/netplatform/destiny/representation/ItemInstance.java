package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ItemInstance {
    @Expose
    private Long cannotEquipReason;
    @Expose
    private Boolean isGridComplete;
    @Expose
    private Long damageTypeStepIndex;
    @Expose
    private Long damageType;
    @Expose
    private Long location;
    @Expose
    private Progression progression;
    @Expose
    private List<Object> stats = new ArrayList<Object>();
    @Expose
    private Long unlockFlagHashRequiredToEquip;
    @Expose
    private List<Object> perks = new ArrayList<Object>();
    @Expose
    private Long damageTypeNodeIndex;
    @Expose
    private Boolean isEquipped;
    @Expose
    private Long itemHash;
    @Expose
    private ArtRegions artRegions;
    @Expose
    private Long transferStatus;
    @Expose
    private Long qualityLevel;
    @Expose
    private Long bindStatus;
    @Expose
    private List<com.bungie.netplatform.destiny.representation.Node> nodes = new ArrayList<com.bungie.netplatform.destiny.representation.Node>();
    @Expose
    private Boolean isEquipment;
    @Expose
    private Long stackSize;
    @Expose
    private String itemInstanceId;
    @Expose
    private Long talentGridHash;
    @Expose
    private Boolean canEquip;
    @Expose
    private Long itemLevel;
    @Expose
    private Boolean useCustomDyes;
    @Expose
    private Long equipRequiredLevel;
    @Expose
    private PrimaryStat primaryStat;

    public PrimaryStat getPrimaryStat() {
        return primaryStat;
    }

    public void setPrimaryStat(PrimaryStat primaryStat) {
        this.primaryStat = primaryStat;
    }

    /**
     * @return The cannotEquipReason
     */
    public Long getCannotEquipReason() {
        return cannotEquipReason;
    }

    /**
     * @param cannotEquipReason The cannotEquipReason
     */
    public void setCannotEquipReason(Long cannotEquipReason) {
        this.cannotEquipReason = cannotEquipReason;
    }

    /**
     * @return The isGridComplete
     */
    public Boolean getIsGridComplete() {
        return isGridComplete;
    }

    /**
     * @param isGridComplete The isGridComplete
     */
    public void setIsGridComplete(Boolean isGridComplete) {
        this.isGridComplete = isGridComplete;
    }

    /**
     * @return The damageTypeStepIndex
     */
    public Long getDamageTypeStepIndex() {
        return damageTypeStepIndex;
    }

    /**
     * @param damageTypeStepIndex The damageTypeStepIndex
     */
    public void setDamageTypeStepIndex(Long damageTypeStepIndex) {
        this.damageTypeStepIndex = damageTypeStepIndex;
    }

    /**
     * @return The damageType
     */
    public Long getDamageType() {
        return damageType;
    }

    /**
     * @param damageType The damageType
     */
    public void setDamageType(Long damageType) {
        this.damageType = damageType;
    }

    /**
     * @return The location
     */
    public Long getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(Long location) {
        this.location = location;
    }

    /**
     * @return The progression
     */
    public Progression getProgression() {
        return progression;
    }

    /**
     * @param progression The progression
     */
    public void setProgression(Progression progression) {
        this.progression = progression;
    }

    /**
     * @return The stats
     */
    public List<Object> getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(List<Object> stats) {
        this.stats = stats;
    }

    /**
     * @return The unlockFlagHashRequiredToEquip
     */
    public Long getUnlockFlagHashRequiredToEquip() {
        return unlockFlagHashRequiredToEquip;
    }

    /**
     * @param unlockFlagHashRequiredToEquip The unlockFlagHashRequiredToEquip
     */
    public void setUnlockFlagHashRequiredToEquip(Long unlockFlagHashRequiredToEquip) {
        this.unlockFlagHashRequiredToEquip = unlockFlagHashRequiredToEquip;
    }

    /**
     * @return The perks
     */
    public List<Object> getPerks() {
        return perks;
    }

    /**
     * @param perks The perks
     */
    public void setPerks(List<Object> perks) {
        this.perks = perks;
    }

    /**
     * @return The damageTypeNodeIndex
     */
    public Long getDamageTypeNodeIndex() {
        return damageTypeNodeIndex;
    }

    /**
     * @param damageTypeNodeIndex The damageTypeNodeIndex
     */
    public void setDamageTypeNodeIndex(Long damageTypeNodeIndex) {
        this.damageTypeNodeIndex = damageTypeNodeIndex;
    }

    /**
     * @return The isEquipped
     */
    public Boolean getIsEquipped() {
        return isEquipped;
    }

    /**
     * @param isEquipped The isEquipped
     */
    public void setIsEquipped(Boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    /**
     * @return The itemHash
     */
    public Long getItemHash() {
        return itemHash;
    }

    /**
     * @param itemHash The itemHash
     */
    public void setItemHash(Long itemHash) {
        this.itemHash = itemHash;
    }

    /**
     * @return The artRegions
     */
    public ArtRegions getArtRegions() {
        return artRegions;
    }

    /**
     * @param artRegions The artRegions
     */
    public void setArtRegions(ArtRegions artRegions) {
        this.artRegions = artRegions;
    }

    /**
     * @return The transferStatus
     */
    public Long getTransferStatus() {
        return transferStatus;
    }

    /**
     * @param transferStatus The transferStatus
     */
    public void setTransferStatus(Long transferStatus) {
        this.transferStatus = transferStatus;
    }

    /**
     * @return The qualityLevel
     */
    public Long getQualityLevel() {
        return qualityLevel;
    }

    /**
     * @param qualityLevel The qualityLevel
     */
    public void setQualityLevel(Long qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    /**
     * @return The bindStatus
     */
    public Long getBindStatus() {
        return bindStatus;
    }

    /**
     * @param bindStatus The bindStatus
     */
    public void setBindStatus(Long bindStatus) {
        this.bindStatus = bindStatus;
    }

    /**
     * @return The nodes
     */
    public List<com.bungie.netplatform.destiny.representation.Node> getNodes() {
        return nodes;
    }

    /**
     * @param nodes The nodes
     */
    public void setNodes(List<com.bungie.netplatform.destiny.representation.Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return The isEquipment
     */
    public Boolean getIsEquipment() {
        return isEquipment;
    }

    /**
     * @param isEquipment The isEquipment
     */
    public void setIsEquipment(Boolean isEquipment) {
        this.isEquipment = isEquipment;
    }

    /**
     * @return The stackSize
     */
    public Long getStackSize() {
        return stackSize;
    }

    /**
     * @param stackSize The stackSize
     */
    public void setStackSize(Long stackSize) {
        this.stackSize = stackSize;
    }

    /**
     * @return The itemInstanceId
     */
    public String getItemInstanceId() {
        return itemInstanceId;
    }

    /**
     * @param itemInstanceId The itemInstanceId
     */
    public void setItemInstanceId(String itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }

    /**
     * @return The talentGridHash
     */
    public Long getTalentGridHash() {
        return talentGridHash;
    }

    /**
     * @param talentGridHash The talentGridHash
     */
    public void setTalentGridHash(Long talentGridHash) {
        this.talentGridHash = talentGridHash;
    }

    /**
     * @return The canEquip
     */
    public Boolean getCanEquip() {
        return canEquip;
    }

    /**
     * @param canEquip The canEquip
     */
    public void setCanEquip(Boolean canEquip) {
        this.canEquip = canEquip;
    }

    /**
     * @return The itemLevel
     */
    public Long getItemLevel() {
        return itemLevel;
    }

    /**
     * @param itemLevel The itemLevel
     */
    public void setItemLevel(Long itemLevel) {
        this.itemLevel = itemLevel;
    }

    /**
     * @return The useCustomDyes
     */
    public Boolean getUseCustomDyes() {
        return useCustomDyes;
    }

    /**
     * @param useCustomDyes The useCustomDyes
     */
    public void setUseCustomDyes(Boolean useCustomDyes) {
        this.useCustomDyes = useCustomDyes;
    }

    /**
     * @return The equipRequiredLevel
     */
    public Long getEquipRequiredLevel() {
        return equipRequiredLevel;
    }

    /**
     * @param equipRequiredLevel The equipRequiredLevel
     */
    public void setEquipRequiredLevel(Long equipRequiredLevel) {
        this.equipRequiredLevel = equipRequiredLevel;
    }
}

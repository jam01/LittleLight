package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CharacterBase {

    @Expose
    private Long buildStatGroupHash;
    @Expose
    private Long genderType;
    @Expose
    private Stats stats;
    @Expose
    private Long lastCompletedStoryHash;
    @Expose
    private Long classType;
    @Expose
    private Long raceHash;
    @Expose
    private Long genderHash;
    @Expose
    private String dateLastPlayed;
    @Expose
    private String characterId;
    @Expose
    private Long currentActivityHash;
    @Expose
    private String minutesPlayedTotal;
    @Expose
    private PeerView peerView;
    @Expose
    private Long membershipType;
    @Expose
    private String membershipId;
    @Expose
    private Long classHash;
    @Expose
    private Long powerLevel;
    @Expose
    private String minutesPlayedThisSession;
    @Expose
    private Customization customization;
    @Expose
    private Long grimoireScore;

    /**
     * @return The buildStatGroupHash
     */
    public Long getBuildStatGroupHash() {
        return buildStatGroupHash;
    }

    /**
     * @param buildStatGroupHash The buildStatGroupHash
     */
    public void setBuildStatGroupHash(Long buildStatGroupHash) {
        this.buildStatGroupHash = buildStatGroupHash;
    }

    /**
     * @return The genderType
     */
    public Long getGenderType() {
        return genderType;
    }

    /**
     * @param genderType The genderType
     */
    public void setGenderType(Long genderType) {
        this.genderType = genderType;
    }

    /**
     * @return The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return The lastCompletedStoryHash
     */
    public Long getLastCompletedStoryHash() {
        return lastCompletedStoryHash;
    }

    /**
     * @param lastCompletedStoryHash The lastCompletedStoryHash
     */
    public void setLastCompletedStoryHash(Long lastCompletedStoryHash) {
        this.lastCompletedStoryHash = lastCompletedStoryHash;
    }

    /**
     * @return The classType
     */
    public Long getClassType() {
        return classType;
    }

    /**
     * @param classType The classType
     */
    public void setClassType(Long classType) {
        this.classType = classType;
    }

    /**
     * @return The raceHash
     */
    public Long getRaceHash() {
        return raceHash;
    }

    /**
     * @param raceHash The raceHash
     */
    public void setRaceHash(Long raceHash) {
        this.raceHash = raceHash;
    }

    /**
     * @return The genderHash
     */
    public Long getGenderHash() {
        return genderHash;
    }

    /**
     * @param genderHash The genderHash
     */
    public void setGenderHash(Long genderHash) {
        this.genderHash = genderHash;
    }

    /**
     * @return The dateLastPlayed
     */
    public String getDateLastPlayed() {
        return dateLastPlayed;
    }

    /**
     * @param dateLastPlayed The dateLastPlayed
     */
    public void setDateLastPlayed(String dateLastPlayed) {
        this.dateLastPlayed = dateLastPlayed;
    }

    /**
     * @return The characterId
     */
    public String getCharacterId() {
        return characterId;
    }

    /**
     * @param characterId The characterId
     */
    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    /**
     * @return The currentActivityHash
     */
    public Long getCurrentActivityHash() {
        return currentActivityHash;
    }

    /**
     * @param currentActivityHash The currentActivityHash
     */
    public void setCurrentActivityHash(Long currentActivityHash) {
        this.currentActivityHash = currentActivityHash;
    }

    /**
     * @return The minutesPlayedTotal
     */
    public String getMinutesPlayedTotal() {
        return minutesPlayedTotal;
    }

    /**
     * @param minutesPlayedTotal The minutesPlayedTotal
     */
    public void setMinutesPlayedTotal(String minutesPlayedTotal) {
        this.minutesPlayedTotal = minutesPlayedTotal;
    }

    /**
     * @return The peerView
     */
    public PeerView getPeerView() {
        return peerView;
    }

    /**
     * @param peerView The peerView
     */
    public void setPeerView(PeerView peerView) {
        this.peerView = peerView;
    }

    /**
     * @return The membershipType
     */
    public Long getMembershipType() {
        return membershipType;
    }

    /**
     * @param membershipType The membershipType
     */
    public void setMembershipType(Long membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * @return The membershipId
     */
    public String getMembershipId() {
        return membershipId;
    }

    /**
     * @param membershipId The membershipId
     */
    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * @return The classHash
     */
    public Long getClassHash() {
        return classHash;
    }

    /**
     * @param classHash The classHash
     */
    public void setClassHash(Long classHash) {
        this.classHash = classHash;
    }

    /**
     * @return The powerLevel
     */
    public Long getPowerLevel() {
        return powerLevel;
    }

    /**
     * @param powerLevel The powerLevel
     */
    public void setPowerLevel(Long powerLevel) {
        this.powerLevel = powerLevel;
    }

    /**
     * @return The minutesPlayedThisSession
     */
    public String getMinutesPlayedThisSession() {
        return minutesPlayedThisSession;
    }

    /**
     * @param minutesPlayedThisSession The minutesPlayedThisSession
     */
    public void setMinutesPlayedThisSession(String minutesPlayedThisSession) {
        this.minutesPlayedThisSession = minutesPlayedThisSession;
    }

    /**
     * @return The customization
     */
    public Customization getCustomization() {
        return customization;
    }

    /**
     * @param customization The customization
     */
    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

    /**
     * @return The grimoireScore
     */
    public Long getGrimoireScore() {
        return grimoireScore;
    }

    /**
     * @param grimoireScore The grimoireScore
     */
    public void setGrimoireScore(Long grimoireScore) {
        this.grimoireScore = grimoireScore;
    }
}

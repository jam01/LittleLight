
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Bonuse {

    @SerializedName("cardId")
    @Expose
    private Long cardId;
    @SerializedName("bonusName")
    @Expose
    private String bonusName;
    @SerializedName("cardName")
    @Expose
    private String cardName;
    @SerializedName("bonusRank")
    @Expose
    private BonusRank bonusRank;
    @SerializedName("statName")
    @Expose
    private String statName;
    @SerializedName("value")
    @Expose
    private Long value;
    @SerializedName("bonusDescription")
    @Expose
    private String bonusDescription;
    @SerializedName("smallImage")
    @Expose
    private SmallImage smallImage;
    @SerializedName("threshold")
    @Expose
    private Long threshold;

    /**
     * @return The cardId
     */
    public Long getCardId() {
        return cardId;
    }

    /**
     * @param cardId The cardId
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    /**
     * @return The bonusName
     */
    public String getBonusName() {
        return bonusName;
    }

    /**
     * @param bonusName The bonusName
     */
    public void setBonusName(String bonusName) {
        this.bonusName = bonusName;
    }

    /**
     * @return The cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName The cardName
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return The bonusRank
     */
    public BonusRank getBonusRank() {
        return bonusRank;
    }

    /**
     * @param bonusRank The bonusRank
     */
    public void setBonusRank(BonusRank bonusRank) {
        this.bonusRank = bonusRank;
    }

    /**
     * @return The statName
     */
    public String getStatName() {
        return statName;
    }

    /**
     * @param statName The statName
     */
    public void setStatName(String statName) {
        this.statName = statName;
    }

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
     * @return The bonusDescription
     */
    public String getBonusDescription() {
        return bonusDescription;
    }

    /**
     * @param bonusDescription The bonusDescription
     */
    public void setBonusDescription(String bonusDescription) {
        this.bonusDescription = bonusDescription;
    }

    /**
     * @return The smallImage
     */
    public SmallImage getSmallImage() {
        return smallImage;
    }

    /**
     * @param smallImage The smallImage
     */
    public void setSmallImage(SmallImage smallImage) {
        this.smallImage = smallImage;
    }

    /**
     * @return The threshold
     */
    public Long getThreshold() {
        return threshold;
    }

    /**
     * @param threshold The threshold
     */
    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }
}

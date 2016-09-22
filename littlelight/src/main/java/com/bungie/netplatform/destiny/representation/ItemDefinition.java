
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ItemDefinition {

    @SerializedName("itemHash")
    @Expose
    public Long itemHash;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("itemDescription")
    @Expose
    public String itemDescription;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("hasIcon")
    @Expose
    public Boolean hasIcon;
    @SerializedName("secondaryIcon")
    @Expose
    public String secondaryIcon;
    @SerializedName("actionName")
    @Expose
    public String actionName;
    @SerializedName("hasAction")
    @Expose
    public Boolean hasAction;
    @SerializedName("deleteOnAction")
    @Expose
    public Boolean deleteOnAction;
    @SerializedName("tierTypeName")
    @Expose
    public String tierTypeName;
    @SerializedName("tierType")
    @Expose
    public Long tierType;
    @SerializedName("itemTypeName")
    @Expose
    public String itemTypeName;
    @SerializedName("bucketTypeHash")
    @Expose
    public Long bucketTypeHash;
    @SerializedName("primaryBaseStatHash")
    @Expose
    public Long primaryBaseStatHash;
    @SerializedName("stats")
    @Expose
    public Stats stats;
    @SerializedName("perkHashes")
    @Expose
    public List<Object> perkHashes = new ArrayList<Object>();
    @SerializedName("specialItemType")
    @Expose
    public Long specialItemType;
    @SerializedName("talentGridHash")
    @Expose
    public Long talentGridHash;
    @SerializedName("hasGeometry")
    @Expose
    public Boolean hasGeometry;
    @SerializedName("statGroupHash")
    @Expose
    public Long statGroupHash;
    @SerializedName("itemLevels")
    @Expose
    public List<Long> itemLevels = new ArrayList<Long>();
    @SerializedName("qualityLevel")
    @Expose
    public Long qualityLevel;
    @SerializedName("equippable")
    @Expose
    public Boolean equippable;
    @SerializedName("instanced")
    @Expose
    public Boolean instanced;
    @SerializedName("rewardItemHash")
    @Expose
    public Long rewardItemHash;
    @SerializedName("values")
    @Expose
    public com.bungie.netplatform.destiny.representation.Values values;
    @SerializedName("itemType")
    @Expose
    public Long itemType;
    @SerializedName("itemSubType")
    @Expose
    public Long itemSubType;
    @SerializedName("classType")
    @Expose
    public Long classType;
    @SerializedName("itemCategoryHashes")
    @Expose
    public List<Long> itemCategoryHashes = new ArrayList<Long>();
    @SerializedName("sourceHashes")
    @Expose
    public List<Object> sourceHashes = new ArrayList<Object>();
    @SerializedName("nonTransferrable")
    @Expose
    public Boolean nonTransferrable;
    @SerializedName("exclusive")
    @Expose
    public Long exclusive;
    @SerializedName("maxStackSize")
    @Expose
    public Long maxStackSize;
    @SerializedName("itemIndex")
    @Expose
    public Long itemIndex;
    @SerializedName("setItemHashes")
    @Expose
    public List<Object> setItemHashes = new ArrayList<Object>();
    @SerializedName("tooltipStyle")
    @Expose
    public String tooltipStyle;
    @SerializedName("questlineItemHash")
    @Expose
    public Long questlineItemHash;
    @SerializedName("needsFullCompletion")
    @Expose
    public Boolean needsFullCompletion;
    @SerializedName("objectiveHashes")
    @Expose
    public List<Object> objectiveHashes = new ArrayList<Object>();
    @SerializedName("allowActions")
    @Expose
    public Boolean allowActions;
    @SerializedName("questTrackingUnlockValueHash")
    @Expose
    public Long questTrackingUnlockValueHash;
    @SerializedName("bountyResetUnlockHash")
    @Expose
    public Long bountyResetUnlockHash;
    @SerializedName("uniquenessHash")
    @Expose
    public Long uniquenessHash;
    @SerializedName("showActiveNodesInTooltip")
    @Expose
    public Boolean showActiveNodesInTooltip;
    @SerializedName("hash")
    @Expose
    public Long hash;
    @SerializedName("index")
    @Expose
    public Long index;

    public Long getHash() {
        return hash;
    }

    public Long getItemHash() {
        return itemHash;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getIcon() {
        return icon;
    }

    public Boolean getHasIcon() {
        return hasIcon;
    }

    public String getSecondaryIcon() {
        return secondaryIcon;
    }

    public String getActionName() {
        return actionName;
    }

    public Boolean getHasAction() {
        return hasAction;
    }

    public Boolean getDeleteOnAction() {
        return deleteOnAction;
    }

    public String getTierTypeName() {
        return tierTypeName;
    }

    public Long getTierType() {
        return tierType;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public Long getBucketTypeHash() {
        return bucketTypeHash;
    }

    public Long getPrimaryBaseStatHash() {
        return primaryBaseStatHash;
    }

    public Stats getStats() {
        return stats;
    }

    public List<Object> getPerkHashes() {
        return perkHashes;
    }

    public Long getSpecialItemType() {
        return specialItemType;
    }

    public Long getTalentGridHash() {
        return talentGridHash;
    }

    public Boolean getHasGeometry() {
        return hasGeometry;
    }

    public Long getStatGroupHash() {
        return statGroupHash;
    }

    public List<Long> getItemLevels() {
        return itemLevels;
    }

    public Long getQualityLevel() {
        return qualityLevel;
    }

    public Boolean getEquippable() {
        return equippable;
    }

    public Boolean getInstanced() {
        return instanced;
    }

    public Long getRewardItemHash() {
        return rewardItemHash;
    }

    public com.bungie.netplatform.destiny.representation.Values getValues() {
        return values;
    }

    public Long getItemType() {
        return itemType;
    }

    public Long getItemSubType() {
        return itemSubType;
    }

    public Long getClassType() {
        return classType;
    }

    public List<Long> getItemCategoryHashes() {
        return itemCategoryHashes;
    }

    public List<Object> getSourceHashes() {
        return sourceHashes;
    }

    public Boolean getNonTransferrable() {
        return nonTransferrable;
    }

    public Long getExclusive() {
        return exclusive;
    }

    public Long getMaxStackSize() {
        return maxStackSize;
    }

    public Long getItemIndex() {
        return itemIndex;
    }

    public List<Object> getSetItemHashes() {
        return setItemHashes;
    }

    public String getTooltipStyle() {
        return tooltipStyle;
    }

    public Long getQuestlineItemHash() {
        return questlineItemHash;
    }

    public Boolean getNeedsFullCompletion() {
        return needsFullCompletion;
    }

    public List<Object> getObjectiveHashes() {
        return objectiveHashes;
    }

    public Boolean getAllowActions() {
        return allowActions;
    }

    public Long getQuestTrackingUnlockValueHash() {
        return questTrackingUnlockValueHash;
    }

    public Long getBountyResetUnlockHash() {
        return bountyResetUnlockHash;
    }

    public Long getUniquenessHash() {
        return uniquenessHash;
    }

    public Boolean getShowActiveNodesInTooltip() {
        return showActiveNodesInTooltip;
    }

    public Long getIndex() {
        return index;
    }
}

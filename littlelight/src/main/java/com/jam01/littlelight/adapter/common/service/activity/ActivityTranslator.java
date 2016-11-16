package com.jam01.littlelight.adapter.common.service.activity;

import com.bungie.netplatform.destiny.representation.Advisors;
import com.bungie.netplatform.destiny.representation.Arena;
import com.bungie.netplatform.destiny.representation.DailyChapterActivities;
import com.bungie.netplatform.destiny.representation.DailyCrucible;
import com.bungie.netplatform.destiny.representation.HeroicStrike;
import com.bungie.netplatform.destiny.representation.Nightfall;
import com.bungie.netplatform.destiny.representation.RaidActivities;
import com.bungie.netplatform.destiny.representation.Tier;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jam01.littlelight.domain.activity.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 11/9/16.
 */
public class ActivityTranslator {
    private Gson gson = new Gson();

    public Map<String, Activity> transform(Advisors advisorsForCharacter) {
        String activityName;
        Map<String, Boolean> levels;

        Map<String, Activity> activityMap = new HashMap<>();
        for (int i = 0; i < advisorsForCharacter.arena.size(); i++) {
            Arena instance = advisorsForCharacter.arena.get(i);
            switch (i) {
                case 0:
                    activityName = "Arena 32";
                    break;
                case 1:
                    activityName = "Arena 34";
                    break;
                case 2:
                    activityName = "Arena 35";
                    break;
                default:
                    activityName = "Unknown Arena";
                    break;
            }
            activityMap.put(activityName, new Activity(activityName, instance.isCompleted, "https://www.bungie.net" + instance.iconPath, null, "Weekly Missions", 1));
        }

        for (Map.Entry<String, JsonElement> instance : advisorsForCharacter.activityAdvisors.entrySet()) {
            JsonElement activityJson = instance.getValue().getAsJsonObject().get("raidActivities");
            if (activityJson != null) {
                RaidActivities raidActivities = gson.fromJson(activityJson, RaidActivities.class);
                levels = new HashMap<>(raidActivities.tiers.size());
                for (Tier tierInstance : raidActivities.tiers) {
                    levels.put(tierInstance.difficultyIdentifier, tierInstance.isCompleted);
                }

                switch (raidActivities.friendlyIdentifier) {
                    case "crota":
                        activityName = "Crota's End";
                        break;
                    case "vaultofglass":
                        activityName = "Vault of Glass";
                        break;
                    case "kingsfall":
                        activityName = "King's Fall";
                        break;
                    case "wrathofthemachine":
                        activityName = "Wrath of the Machine";
                        break;
                    default:
                        activityName = "Unknown Raid";
                        break;
                }

                activityMap.put(activityName, new Activity(activityName,
                        false,
                        "https://www.bungie.net" + raidActivities.iconPath,
                        levels, "Raids", 2));
            }

            activityJson = instance.getValue().getAsJsonObject().get("dailyCrucible");
            if (activityJson != null) {
                DailyCrucible dailyCrucible = gson.fromJson(activityJson, DailyCrucible.class);
                activityMap.put("Daily Crucible", new Activity("Daily Crucible", dailyCrucible.isCompleted, "https://www.bungie.net" + dailyCrucible.iconPath, null, "Daily Missions", 0));
            }


            activityJson = instance.getValue().getAsJsonObject().get("dailyChapterActivities");
            if (activityJson != null) {
                DailyChapterActivities dailyChapterActivities = gson.fromJson(activityJson, DailyChapterActivities.class);
                activityMap.put("Daily Story", new Activity("Daily Story", dailyChapterActivities.isCompleted, "https://www.bungie.net" + dailyChapterActivities.iconPath, null, "Daily Missions", 0));
            }


            activityJson = instance.getValue().getAsJsonObject().get("heroicStrike");
            if (activityJson != null) {
                HeroicStrike heroicStrike = gson.fromJson(activityJson, HeroicStrike.class);
                levels = new HashMap<>(heroicStrike.tiers.size());
                for (Tier tierInstance : heroicStrike.tiers) {
                    levels.put(tierInstance.difficultyIdentifier, tierInstance.isCompleted);
                }

                activityMap.put("Weekly Heroic", new Activity("Weekly Heroic", false, "https://www.bungie.net" + heroicStrike.iconPath, levels, "Weekly Missions", 1));
            }


            activityJson = instance.getValue().getAsJsonObject().get("nightfall");
            if (activityJson != null) {
                Nightfall nightfall = gson.fromJson(activityJson, Nightfall.class);
                levels = new HashMap<>(nightfall.tiers.size());
                for (Tier tierInstance : nightfall.tiers) {
                    levels.put(tierInstance.difficultyIdentifier, tierInstance.isCompleted);
                }

                activityMap.put("Weekly Nightfall", new Activity("Weekly Nightfall", false, "https://www.bungie.net" + nightfall.iconPath, levels, "Weekly Missions", 1));
            }
        }
        return activityMap;
    }
}

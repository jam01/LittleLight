package com.jam01.littlelight.domain.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 11/9/16.
 */
public class Character {
    private final String characterId;
    private final Map<String, Activity> activityMap;

    public Character(String characterId, Map<String, Activity> activityMap) {
        this.characterId = characterId;
        this.activityMap = activityMap;
    }

    public String characterId() {
        return characterId;
    }

    public Activity getDailyCrucible() {
        return activityMap.get("dailyCrucible");
    }

    public Activity getDailyStory() {
        return activityMap.get("dailyStory");
    }

    public List<Activity> getRaids() {
        List<Activity> raids = new ArrayList<>();
        raids.add(activityMap.get("moonRaid"));
        raids.add(activityMap.get("venusRaid"));
        raids.add(activityMap.get("mercuryRaid"));
        return raids;
    }

    public Activity getNightfall() {
        return activityMap.get("nightfall");
    }

    public Activity getHeroic() {
        return activityMap.get("heroic");
    }


    public Collection<Activity> allActivities() {
        return activityMap.values();
    }

}

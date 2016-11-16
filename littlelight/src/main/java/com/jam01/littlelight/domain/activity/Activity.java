package com.jam01.littlelight.domain.activity;

import java.util.Map;

/**
 * Created by jam01 on 11/9/16.
 */
public class Activity {
    private final String name;
    private final boolean completed;
    private final String iconUrl;
    private final Map<String, Boolean> levels;
    private final String activityType;
    private final int activityTypeInt;

    public Activity(String name, boolean completed, String iconUrl, Map<String, Boolean> levels, String activityType, int activityTypeInt) {
        this.name = name;
        this.completed = completed;
        this.iconUrl = iconUrl;
        this.levels = levels;
        this.activityType = activityType;

        this.activityTypeInt = activityTypeInt;
    }

    public String getActivityType() {
        return activityType;
    }

    public int getActivityTypeInt() {
        return activityTypeInt;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Map<String, Boolean> levels() {
        return levels;
    }
}

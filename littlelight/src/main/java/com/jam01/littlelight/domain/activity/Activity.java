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

    public Activity(String name, boolean completed, String iconUrl, Map<String, Boolean> levels) {
        this.name = name;
        this.completed = completed;
        this.iconUrl = iconUrl;
        this.levels = levels;
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

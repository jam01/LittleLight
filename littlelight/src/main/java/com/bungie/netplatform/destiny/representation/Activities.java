package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Activities {

    @Expose
    private String dateActivityStarted;
    @Expose
    private List<Available> available = new ArrayList<Available>();

    /**
     * @return The dateActivityStarted
     */
    public String getDateActivityStarted() {
        return dateActivityStarted;
    }

    /**
     * @param dateActivityStarted The dateActivityStarted
     */
    public void setDateActivityStarted(String dateActivityStarted) {
        this.dateActivityStarted = dateActivityStarted;
    }

    /**
     * @return The available
     */
    public List<Available> getAvailable() {
        return available;
    }

    /**
     * @param available The available
     */
    public void setAvailable(List<Available> available) {
        this.available = available;
    }
}

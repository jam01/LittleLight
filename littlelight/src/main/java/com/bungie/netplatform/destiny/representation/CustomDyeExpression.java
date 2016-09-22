package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CustomDyeExpression {

    @Expose
    private List<Object> steps = new ArrayList<Object>();

    /**
     * @return The steps
     */
    public List<Object> getSteps() {
        return steps;
    }

    /**
     * @param steps The steps
     */
    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

}

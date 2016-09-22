package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PeerView {

    @Expose
    private List<Equipment> equipment = new ArrayList<Equipment>();

    /**
     * @return The equipment
     */
    public List<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipment The equipment
     */
    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

}

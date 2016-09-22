package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Account {

    @Expose
    private String membershipId;
    @Expose
    private Long membershipType;
    @Expose
    private List<Character> characters = new ArrayList<Character>();
    @Expose
    private Inventory inventory;
    @Expose
    private Long grimoireScore;

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
     * @return The characters
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * @param characters The characters
     */
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    /**
     * @return The inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param inventory The inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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

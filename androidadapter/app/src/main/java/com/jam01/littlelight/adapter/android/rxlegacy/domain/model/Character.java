package com.jam01.littlelight.adapter.android.rxlegacy.domain.model;

/**
 * Created by jam01 on 4/7/15.
 */
public class Character {
    private String emblemPath;
    private String emblemBackgroundPath;
    private String nickName;
    private String characterId;
    private String classTypeName;
    private String raceName;
    private String genderName;
    private int grimoireScore;
    private int characterLevel;
    private int classType;
    private int light;
    private int defense;
    private int intellect;
    private int discipline;
    private int strength;
    private int raceType;
    private int genderType;
    private int armor;
    private int recovery;
    private int agility;

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getRaceType() {
        return raceType;
    }

    public void setRaceType(int raceType) {
        this.raceType = raceType;
        switch (raceType) {
            case 0: {
                this.setRaceName("Human");
                break;
            }
            case 1: {
                this.setRaceName("Awoken");
                break;
            }
            case 2: {
                this.setRaceName("Exo");
                break;
            }
        }
    }

    public int getGenderType() {
        return genderType;
    }

    public void setGenderType(int genderType) {
        this.genderType = genderType;

        switch (genderType) {
            case 0: {
                this.setGenderName("Male");
                break;
            }
            case 1: {
                this.setGenderName("Female");
                break;
            }
        }
    }

    public String getNickName() {
        if (nickName != null)
            return nickName;
        else return classTypeName + " " + characterLevel;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public String getEmblemPath() {
        return emblemPath;
    }

    public void setEmblemPath(String emblemPath) {
        this.emblemPath = emblemPath;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
        switch (classType) {
            case 0: {
                this.setClassTypeName("Titan");
                break;
            }
            case 1: {
                this.setClassTypeName("Hunter");
                break;
            }
            case 2: {
                this.setClassTypeName("Warlock");
                break;
            }
        }
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getEmblemBackgroundPath() {
        return emblemBackgroundPath;
    }

    public void setEmblemBackgroundPath(String emblemBackgroundPath) {
        this.emblemBackgroundPath = emblemBackgroundPath;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public int getGrimoireScore() {
        return grimoireScore;
    }

    public void setGrimoireScore(int grimoireScore) {
        this.grimoireScore = grimoireScore;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}


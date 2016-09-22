package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.identityaccess.AccountId;

/**
 * Created by jam01 on 8/4/16.
 */
public class Character {
    private final String gender;
    private final String characterId;
    private final String className;
    private final String race;
    private final AccountId accountId;
    private String name;
    private String emblemPath;
    private String emblemBackgroundPath;
    private int level;
    private int light;
    private int defense;
    private int intellect;
    private int discipline;
    private int strength;
    private int armor;
    private int recovery;
    private int agility;

    public Character(String race, String name, String gender, String emblemPath, String emblemBackgroundPath, String characterId,
                     String className, int level, int light, int defense, int intellect, int discipline, int strength,
                     int armor, int recovery, int agility, AccountId accountId) {
        this.race = race;
        this.name = name;
        this.gender = gender;
        this.emblemPath = emblemPath;
        this.emblemBackgroundPath = emblemBackgroundPath;
        this.characterId = characterId;
        this.className = className;
        this.level = level;
        this.light = light;
        this.defense = defense;
        this.intellect = intellect;
        this.discipline = discipline;
        this.strength = strength;
        this.armor = armor;
        this.recovery = recovery;
        this.agility = agility;
        this.accountId = accountId;
    }

    private Character(Builder builder) {
        this.race = builder.race;
        this.name = builder.name;
        this.gender = builder.gender;
        this.emblemPath = builder.emblemPath;
        this.emblemBackgroundPath = builder.emblemBackgroundPath;
        this.characterId = builder.characterId;
        this.className = builder.className;
        this.level = builder.level;
        this.light = builder.light;
        this.defense = builder.defense;
        this.intellect = builder.intellect;
        this.discipline = builder.discipline;
        this.strength = builder.strength;
        this.armor = builder.armor;
        this.recovery = builder.recovery;
        this.agility = builder.agility;
        this.accountId = builder.accountId;
    }


    public String name() {
        return name;
    }

    public String gender() {
        return gender;
    }

    public String emblemPath() {
        return emblemPath;
    }

    public String emblemBackgroundPath() {
        return emblemBackgroundPath;
    }

    public String characterId() {
        return characterId;
    }

    public String className() {
        return className;
    }

    public String race() {
        return race;
    }

    public int level() {
        return level;
    }

    public int light() {
        return light;
    }

    public int defense() {
        return defense;
    }

    public int intellect() {
        return intellect;
    }

    public int discipline() {
        return discipline;
    }

    public int strength() {
        return strength;
    }

    public int armor() {
        return armor;
    }

    public int recovery() {
        return recovery;
    }

    public int agility() {
        return agility;
    }

    public static class Builder {
        //Required parameters
        private final String characterId;
        private final AccountId accountId;

        //Optional parameters
        private String name;
        private String className;
        private String gender;
        private String race;
        private String emblemPath;
        private String emblemBackgroundPath;
        private int level;
        private int light;
        private int defense;
        private int intellect;
        private int discipline;
        private int strength;
        private int armor;
        private int recovery;
        private int agility;

        public Builder(String characterId, AccountId accountId) {
            this.characterId = characterId;
            this.accountId = accountId;
        }


        public Builder level(int val) {
            level = val;
            return this;
        }

        public Builder light(int val) {
            light = val;
            return this;
        }

        public Builder defense(int val) {
            defense = val;
            return this;
        }

        public Builder intellect(int val) {
            intellect = val;
            return this;
        }

        public Builder discipline(int val) {
            discipline = val;
            return this;
        }

        public Builder strength(int val) {
            strength = val;
            return this;
        }

        public Builder armor(int val) {
            armor = val;
            return this;
        }

        public Builder agility(int val) {
            agility = val;
            return this;
        }

        public Builder recovery(int val) {
            recovery = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder className(String val) {
            className = val;
            return this;
        }

        public Builder gender(String val) {
            gender = val;
            return this;
        }

        public Builder race(String val) {
            race = val;
            return this;
        }

        public Builder emblemPath(String val) {
            emblemPath = val;
            return this;
        }

        public Builder emblemBackgroundPath(String val) {
            emblemBackgroundPath = val;
            return this;
        }

        public Character build() {
            return new Character(this);
        }
    }
}

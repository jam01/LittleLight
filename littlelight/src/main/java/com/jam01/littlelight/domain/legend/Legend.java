package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.lang.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jam01 on 7/25/16.
 */
public class Legend {
    private final AccountId id;
    private final String platform;
    private Map<String, Character> characterMap;
    private int grimoireScore;

    public Legend(AccountId id, String platform, Collection<Character> characters, int grimoireScore) {
        this.id = id;
        this.platform = platform;
        this.grimoireScore = grimoireScore;

        characterMap = new HashMap<>(characters.size());
        for (Character instance : characters) {
            characterMap.put(instance.characterId(), instance);
        }
    }

    public Set<String> withCharacterIds() {
        return characterMap.keySet();
    }

    public Collection<Character> withCharacters() {
        return characterMap.values();
    }

    public AccountId withId() {
        return id;
    }

    public String onPlatform() {
        return platform;
    }

    public int withGrimoire() {
        return grimoireScore;
    }

}

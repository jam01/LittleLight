package com.jam01.littlelight.domain.activity;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 11/8/16.
 */
public class Account {
    private final AccountId id;
    private Map<String, Character> characterMap;

    public Account(AccountId id, Collection<Character> characters) {
        this.id = id;

        characterMap = new HashMap<>(characters.size());
        for (Character instance : characters) {
            characterMap.put(instance.characterId(), instance);
        }
    }

    public Collection<Character> withCharacters() {
        return Collections.unmodifiableCollection(characterMap.values());
    }

    public AccountId withId() {
        return id;
    }
}

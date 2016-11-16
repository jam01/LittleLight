package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 7/25/16.
 */
public class Legend {
    private final AccountId id;
    private Map<String, Character> characterMap;
    private int grimoireScore;

    public Legend(AccountId id, Collection<Character> characters, int grimoireScore) {
        this.id = id;
        this.grimoireScore = grimoireScore;

        characterMap = new HashMap<>(characters.size());
        for (Character instance : characters) {
            characterMap.put(instance.characterId(), instance);
        }
    }

    public Collection<Character> withCharacters() {
        return Collections.unmodifiableCollection(characterMap.values());
    }

    public Character withId(String id) {
        for (Character charac : withCharacters()) {
            if (charac.characterId().equals(id))
                return charac;
        }
        return null;
    }

    public AccountId withId() {
        return id;
    }

    public int withGrimoire() {
        return grimoireScore;
    }

    public void updateFrom(Legend newState) {
        if (newState.withGrimoire() != grimoireScore) {
            grimoireScore = newState.withGrimoire();
        }
        characterMap.clear();
        for (Character instance : newState.withCharacters()) {
            characterMap.put(instance.characterId(), instance);
        }

        DomainEventPublisher.instanceOf().publish(new LegendUpdated(this));
    }
}

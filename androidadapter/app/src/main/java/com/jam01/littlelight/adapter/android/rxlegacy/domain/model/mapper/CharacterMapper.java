package com.jam01.littlelight.adapter.android.rxlegacy.domain.model.mapper;

import com.bungie.netplatform.Character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 4/29/16.
 */
public class CharacterMapper {
    public static com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character transform(Character characterEntity) {
        com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character character = null;
        if (characterEntity != null) {
            character = new com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character();
            character.setCharacterId(characterEntity.getCharacterBase().getCharacterId());
            character.setEmblemPath(characterEntity.getEmblemPath());
            character.setEmblemBackgroundPath((characterEntity.getBackgroundPath()));
            character.setClassType(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getClassType())));
            character.setCharacterLevel(Integer.valueOf(String.valueOf(characterEntity.getCharacterLevel())));

            character.setRaceType(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getClassType())));
            character.setGenderType(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getGenderType())));
            character.setGrimoireScore(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getGrimoireScore())));
            character.setLight(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getPowerLevel())));
            character.setDefense(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_DEFENSE().getValue())));
            character.setIntellect(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_INTELLECT().getValue())));
            character.setDiscipline(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_DISCIPLINE().getValue())));
            character.setStrength(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_STRENGTH().getValue())));

            character.setArmor(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_ARMOR().getValue())));
            character.setRecovery(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_RECOVERY().getValue())));
            character.setAgility(Integer.valueOf(String.valueOf(characterEntity.getCharacterBase().getStats().getSTAT_AGILITY().getValue())));
        }
        return character;
    }

    public static List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character> transform(Collection<Character> characterCollection) {
        List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character> characterList = new ArrayList<>(characterCollection.size());
        com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character character;

        for (Character characterEntity : characterCollection) {
            character = transform(characterEntity);
            if (character != null)
                characterList.add(character);
        }

        return characterList;
    }
}

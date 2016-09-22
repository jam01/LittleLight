package com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper;

import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character;
import com.jam01.littlelight.adapter.android.legacy.Helpers.Endpoints;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 4/29/16.
 */
public class CharacterLegendMapper {
    public static CharacterLegend transform(Character character) {
        CharacterLegend characterLegend = null;
        if (character != null) {
            characterLegend = new CharacterLegend();
            characterLegend.setCharacterId(character.getCharacterId());
            characterLegend.setEmblemPath(Endpoints.BASE_URL + character.getEmblemPath());
            characterLegend.setEmblemBackgroundPath(Endpoints.BASE_URL + character.getEmblemBackgroundPath());
            characterLegend.setCharacterLevel(character.getCharacterLevel());
            characterLegend.setGrimoireScore(character.getGrimoireScore());
            characterLegend.setLight(character.getLight());
            characterLegend.setDefense(character.getDefense());
            characterLegend.setIntellect(character.getIntellect());
            characterLegend.setDiscipline(character.getDiscipline());
            characterLegend.setStrength(character.getStrength());
            characterLegend.setArmor(character.getArmor());
            characterLegend.setRecovery(character.getRecovery());
            characterLegend.setAgility(character.getAgility());
            characterLegend.setClassType(character.getClassType());
            characterLegend.setNickName(character.getNickName());
        }
        return characterLegend;
    }

    public static List<CharacterLegend> transform(Collection<Character> characterCollection) {
        List<CharacterLegend> characterLegendList = new ArrayList<>(characterCollection.size());
        CharacterLegend characterLegend;

        for (Character character : characterCollection) {
            characterLegend = transform(character);
            if (character != null)
                characterLegendList.add(characterLegend);
        }

        return characterLegendList;
    }
}

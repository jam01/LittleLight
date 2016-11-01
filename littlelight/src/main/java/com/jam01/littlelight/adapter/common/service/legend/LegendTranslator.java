package com.jam01.littlelight.adapter.common.service.legend;

import com.bungie.netplatform.destiny.representation.Account;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Character;
import com.jam01.littlelight.domain.legend.Legend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 8/4/16.
 */
public class LegendTranslator {

    public Legend from(Account bungieAccount) {
        AccountId newId = new AccountId(bungieAccount.getMembershipType().intValue(), bungieAccount.getMembershipId());
        List<Character> characters = new ArrayList<>(bungieAccount.getCharacters().size());
        for (com.bungie.netplatform.destiny.representation.Character instance : bungieAccount.getCharacters()) {
            characters.add(transform(instance, newId));
        }

        return new Legend(newId,
                characters,
                bungieAccount.getGrimoireScore().intValue());
    }

    private Character transform(com.bungie.netplatform.destiny.representation.Character bungieCharacter, AccountId aAccountId) {
        Character.Builder builder = new Character.Builder(bungieCharacter.getCharacterBase().getCharacterId(), aAccountId);

        builder.emblemPath(bungieCharacter.getEmblemPath());
        builder.emblemBackgroundPath((bungieCharacter.getBackgroundPath()));
        builder.light(bungieCharacter.getCharacterLevel().intValue());
        builder.level(bungieCharacter.getCharacterBase().getPowerLevel().intValue());
        builder.defense(bungieCharacter.getCharacterBase().getStats().getSTAT_DEFENSE().getValue().intValue());
        builder.intellect(bungieCharacter.getCharacterBase().getStats().getSTAT_INTELLECT().getValue().intValue());
        builder.discipline(bungieCharacter.getCharacterBase().getStats().getSTAT_DISCIPLINE().getValue().intValue());
        builder.strength(bungieCharacter.getCharacterBase().getStats().getSTAT_STRENGTH().getValue().intValue());
        builder.armor(bungieCharacter.getCharacterBase().getStats().getSTAT_ARMOR().getValue().intValue());
        builder.recovery(bungieCharacter.getCharacterBase().getStats().getSTAT_RECOVERY().getValue().intValue());
        builder.agility(bungieCharacter.getCharacterBase().getStats().getSTAT_AGILITY().getValue().intValue());

        switch (bungieCharacter.getCharacterBase().getClassType().intValue()) {
            case 0:
                builder.className("Titan");
                break;
            case 1:
                builder.className("Hunter");
                break;
            case 2:
                builder.className("Warlock");
                break;
        }

        switch (bungieCharacter.getCharacterBase().getGenderType().intValue()) {
            case 0:
                builder.gender("Male");
                break;
            case 1:
                builder.gender("Female");
                break;
        }

        return builder.build();
    }
}

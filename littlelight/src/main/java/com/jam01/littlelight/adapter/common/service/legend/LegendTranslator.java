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
                newId.withMembershipType() == 1 ? "Xbox Live" : "PlayStation Network",
                characters,
                bungieAccount.getGrimoireScore().intValue());
    }

    private Character transform(com.bungie.netplatform.destiny.representation.Character bungieCharacter, AccountId aAccountId) {
        Character.Builder builder = new Character.Builder(bungieCharacter.getCharacterBase().getCharacterId(), aAccountId);

        builder.emblemPath(bungieCharacter.getEmblemPath());
        builder.emblemBackgroundPath((bungieCharacter.getBackgroundPath()));
        builder.level(bungieCharacter.getCharacterLevel().intValue());
        builder.light(bungieCharacter.getCharacterBase().getPowerLevel().intValue());
        builder.defense(bungieCharacter.getCharacterBase().getStats().getSTAT_DEFENSE().getValue().intValue());
        builder.discipline(bungieCharacter.getCharacterBase().getStats().getSTAT_DISCIPLINE().getValue().intValue());
        builder.strength(bungieCharacter.getCharacterBase().getStats().getSTAT_STRENGTH().getValue().intValue());
        builder.armor(bungieCharacter.getCharacterBase().getStats().getSTAT_ARMOR().getValue().intValue());
        builder.recovery(bungieCharacter.getCharacterBase().getStats().getSTAT_RECOVERY().getValue().intValue());
        builder.agility(bungieCharacter.getCharacterBase().getStats().getSTAT_AGILITY().getValue().intValue());

        switch (bungieCharacter.getCharacterBase().getClassType().intValue()) {
            case 0:
                builder.className("Titan");

            case 1:
                builder.className("Hunter");

            case 2:
                builder.className("Warlock");

        }

        switch (bungieCharacter.getCharacterBase().getGenderType().intValue()) {
            case 0:
                builder.className("Male");

            case 1:
                builder.className("Female");
        }

        return builder.build();
    }
}

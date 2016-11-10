package com.jam01.littlelight.adapter.common.service.activity;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.Advisors;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.jam01.littlelight.adapter.common.service.BungieResponseValidator;
import com.jam01.littlelight.domain.activity.Account;
import com.jam01.littlelight.domain.activity.Character;
import com.jam01.littlelight.domain.activity.DestinyActivityService;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 11/9/16.
 */
public class ACLActivityService implements DestinyActivityService {
    private final DestinyApi destinyApi;
    private Map<AccountId, List<String>> characterIdsCached = new HashMap<>();

    public ACLActivityService(DestinyApi destinyApi) {
        this.destinyApi = destinyApi;
    }

    @Override
    public Account getFor(com.jam01.littlelight.domain.identityaccess.Account anAccount) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();

        if (characterIdsCached.get(anAccountId) == null) {
            BungieResponse<DataResponse<com.bungie.netplatform.destiny.representation.Account>> accountRespose = destinyApi.getAccount(anAccountId.withMembershipType(), anAccountId.withMembershipId(),
                    credentials.asCookieVal(), credentials.xcsrf());
            BungieResponseValidator.validate(accountRespose, anAccount);
            List<String> characterIds = new ArrayList<>(accountRespose.getResponse().getData().getCharacters().size());
            for (com.bungie.netplatform.destiny.representation.Character bungieCharacter : accountRespose.getResponse().getData().getCharacters()) {
                characterIds.add(bungieCharacter.getCharacterBase().getCharacterId());
                characterIdsCached.put(anAccountId, characterIds);
            }
        }

        List<Character> characters = new ArrayList<>(characterIdsCached.size());
        ActivityTranslator translator = new ActivityTranslator();
        for (String id : characterIdsCached.get(anAccountId)) {
            BungieResponse<DataResponse<Advisors>> advisorsResponse = destinyApi.getAdvisorsForCharacter(anAccountId.withMembershipType(), anAccountId.withMembershipId(), id, credentials.asCookieVal(), credentials.xcsrf());
            BungieResponseValidator.validate(advisorsResponse, anAccount);
            characters.add(new Character(id, translator.transform(advisorsResponse.getResponse().getData())));
        }

        return new Account(anAccountId, characters);
    }
}

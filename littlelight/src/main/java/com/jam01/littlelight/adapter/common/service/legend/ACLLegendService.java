package com.jam01.littlelight.adapter.common.service.legend;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.jam01.littlelight.adapter.common.service.BungieResponseValidator;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.DestinyLegendService;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendRepository;

/**
 * Created by jam01 on 7/30/16.
 */
public class ACLLegendService implements DestinyLegendService {
    private final DestinyApi destinyApi;

    public ACLLegendService(DestinyApi destinyApi) {
        this.destinyApi = destinyApi;
    }

    @Override
    public void synchronizeLegendFor(Account anAccount, LegendRepository repository) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();
        Legend toUpdate;

        BungieResponse<DataResponse<com.bungie.netplatform.destiny.representation.Account>> accountResponse = destinyApi
                .getAccount((anAccountId.withMembershipType()),
                        anAccountId.withMembershipId(),
                        credentials.asCookieVal(),
                        credentials.xcsrf());

        BungieResponseValidator.validate(accountResponse, anAccount);
        com.bungie.netplatform.destiny.representation.Account bungieAccount = accountResponse.getResponse().getData();
        Legend newState = new LegendTranslator().from(bungieAccount);

        if (repository.hasOfAccount(anAccountId)) {
            toUpdate = repository.ofId(anAccountId);
            toUpdate.updateFrom(newState);
        } else {
            repository.add(newState);
        }
    }
}

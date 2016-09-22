package com.jam01.littlelight.adapter.android.legend.service;


import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.User;
import com.jam01.littlelight.adapter.android.common.RetrofitDestinyApiFacade;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.CredentialsService;
import com.jam01.littlelight.domain.legend.Legend;

/**
 * Created by jam01 on 7/30/16.
 */
public class LegendAdapter implements DestinyLegendAdapter {
    private CredentialsService credentialsService;
    private DestinyApi apiFacade = RetrofitDestinyApiFacade.getInstance();

    @Override
    public Legend ofId(AccountId aAccountId) {
        LegendTranslator translator = new LegendTranslator();

        Account bungieAccount = apiFacade.getAccount(String.valueOf(aAccountId.withMembershipType()),
                aAccountId.withMembershipId(),
                credentialsService.ofLegend(aAccountId).asCookieVal(),
                credentialsService.ofLegend(aAccountId).xcsrf());

        User bungieUser = apiFacade.getUser(
                credentialsService.ofLegend(aAccountId).asCookieVal(),
                credentialsService.ofLegend(aAccountId).xcsrf());

        return translator.from(bungieAccount);
    }

//    @Override
//    public boolean authenticate(Legend anLegend) {
//        try {
//            apiFacade.getUser(anLegend.withCredentials().asCookieVal(), anLegend.withCredentials().getBungled().substring(8));
//            return true;
//        } catch (Exception exception) {
//            //Any exception thrown means the account credentials are no longer valid
//            return false;
//        }
//    }
}

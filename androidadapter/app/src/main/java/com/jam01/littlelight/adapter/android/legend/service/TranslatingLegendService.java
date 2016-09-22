package com.jam01.littlelight.adapter.android.legend.service;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.DestinyLegendService;
import com.jam01.littlelight.domain.legend.Legend;

/**
 * Created by jam01 on 7/30/16.
 */
public class TranslatingLegendService implements DestinyLegendService {
    private DestinyLegendAdapter accountAdapter;

    @Override
    public Legend ofId(AccountId anAccountId) {
        return accountAdapter.ofId(anAccountId);
    }
}

package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.identityaccess.AccountId;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyLegendService {
    Legend ofId(AccountId anAccountId);
}

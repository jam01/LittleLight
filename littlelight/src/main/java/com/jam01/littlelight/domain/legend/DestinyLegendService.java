package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.identityaccess.Account;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyLegendService {

    void synchronizeLegendFor(Account anAccount, LegendRepository repository);
}

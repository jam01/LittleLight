package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.activity.Account;
import com.jam01.littlelight.domain.activity.DestinyActivityService;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;

/**
 * Created by jam01 on 11/9/16.
 */
public class ActivityService {
    private DestinyActivityService destinyService;
    private User user;

    public ActivityService(DestinyActivityService destinyService, User user) {
        this.destinyService = destinyService;
        this.user = user;
    }

    public Account ofAccount(AccountId anAccountId) {
        return destinyService.getFor(user.ofId(anAccountId));
    }
}

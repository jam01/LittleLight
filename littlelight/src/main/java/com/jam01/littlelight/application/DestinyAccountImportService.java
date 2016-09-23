package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;

/**
 * Created by jam01 on 9/22/16.
 */
public class DestinyAccountImportService {
    private DestinyAccountService destinyService;
    private User user;

    public DestinyAccountImportService(DestinyAccountService destinyService, User user) {
        this.destinyService = destinyService;
        this.user = user;
    }

    public void loadAccountFrom(int membershipType, AccountCredentials credentials) {
        user.registerAccount(destinyService.accountOf(membershipType, credentials));
    }
}

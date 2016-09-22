package com.jam01.littlelight.adapter.android.rxlegacy.domain.model.mapper;

import com.bungie.netplatform.Account;

/**
 * Created by jam01 on 4/29/16.
 */
public class AccountMapper {
    public static com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Account transform(Account accountEntity) {
        com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Account account = null;
        if (accountEntity != null) {
            account = new com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Account();
            account.setMembershipId(accountEntity.getMembershipId());
            account.setMembershipType(Integer.valueOf(String.valueOf(accountEntity.getMembershipType())));
            account.setCharacterList(CharacterMapper.transform(accountEntity.getCharacters()));
        }
        return account;
    }
}

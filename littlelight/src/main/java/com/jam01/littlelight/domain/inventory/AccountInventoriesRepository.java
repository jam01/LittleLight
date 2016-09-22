package com.jam01.littlelight.domain.inventory;

import java.util.Collection;

/**
 * Created by jam01 on 9/21/16.
 */
public interface AccountInventoriesRepository {
    Collection<AccountInventories> all();

    AccountInventories thatContains(String anInventoryId);

    void add(AccountInventories AccountInventories);

    void addAll(Collection<AccountInventories> inventories);

    void remove(AccountInventories AccountInventories);

    void removeAll();
}

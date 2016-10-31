package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;

/**
 * Created by jam01 on 7/25/16.
 */
public interface LegendRepository {
    boolean isEmpty();

    Legend ofId(AccountId anAccountId);

    boolean hasOfAccount(AccountId anAccountId);

    void save(Legend anLegend);

    void addAll(Collection<Legend> legends);

    void remove(Legend anLegend);

    void removeAll();

    Collection<Legend> all();

    void add(Legend aLegend);
}

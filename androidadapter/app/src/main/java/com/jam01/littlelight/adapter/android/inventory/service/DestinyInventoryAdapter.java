package com.jam01.littlelight.adapter.android.inventory.service;

import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.legend.Legend;

import java.util.Collection;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyInventoryAdapter {
    Collection<Inventory> ofLegend(Legend anLegend);

    boolean take(Item anItem, Inventory toCharacter);

    boolean store(Item anItem, Inventory fromCharacter);

    boolean equip(Item anItem, Inventory onCharacter);
}

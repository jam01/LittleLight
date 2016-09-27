package com.jam01.littlelight.adapter.common.presentation;

import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.legend.Legend;

/**
 * Created by jam01 on 9/25/16.
 */
public class InventoryDPO {
    public final Inventory inventory;
    public final Legend legend;

    public InventoryDPO(Inventory inventory, Legend legend) {
        this.inventory = inventory;
        this.legend = legend;
    }
}

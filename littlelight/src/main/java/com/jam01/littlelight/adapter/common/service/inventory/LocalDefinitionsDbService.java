package com.jam01.littlelight.adapter.common.service.inventory;

import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;

import java.util.List;

/**
 * Created by jam01 on 11/7/16.
 */
public interface LocalDefinitionsDbService {
    public List<ItemDefinition> getDefinitionsFor(List<ItemInstance> instanceList);
}

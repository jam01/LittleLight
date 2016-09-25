package com.bungie.netplatform.destiny.representation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 4/21/15.
 */
public class Globals {
    public static final HashMap<Long, String> buckets = new HashMap<>(16);
    public static final HashMap<Integer, String> damageTypes = new HashMap<>(5);

    static {
        buckets.put(0L, "All");
        buckets.put(3865314626L, "Materials");
        buckets.put(1469714392L, "Consumables");
        buckets.put(3284755031L, "Subclass");
        buckets.put(1498876634L, "Primary Weapons");
        buckets.put(2465295065L, "Secondary Weapons");
        buckets.put(953998645L, "Heavy Weapons");
        buckets.put(3448274439L, "Helmets");
        buckets.put(3551918588L, "Arms");
        buckets.put(14239492L, "Chests");
        buckets.put(20886954L, "Legs");
        buckets.put(1585787867L, "Class Items");
        buckets.put(4023194814L, "Ghosts");
        buckets.put(2025709351L, "Vehicles");
        buckets.put(284967655L, "Ships");
        buckets.put(2973005342L, "Shaders");
        buckets.put(4274335291L, "Emblems");
        buckets.put(2197472680L, "Quests");
        buckets.put(375726501L, "Mission Items");
    }

    static {
        damageTypes.put(0, "None");
        damageTypes.put(1, "Kinetic");
        damageTypes.put(2, "Arc");
        damageTypes.put(3, "Solar");
        damageTypes.put(4, "Void");
        damageTypes.put(9, "All");
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}

package com.bungie.netplatform.destiny.representation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 4/21/15.
 */
public class Globals {
    public static final HashMap<Long, String> buckets = new HashMap<>(16);
    public static final HashMap<Integer, String> damageTypes = new HashMap<>(5);
    public static final HashMap<Long, String> classTypes = new HashMap<>(3);
    public static final HashMap<Integer, String> tierTypes = new HashMap<>(6);

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
        buckets.put(2197472680L, "Bounties");
        buckets.put(375726501L, "Mission Items");
        buckets.put(3054419239L, "Emotes");
        buckets.put(3796357825L, "Sparrow Horns");
        buckets.put(434908299L, "Artifacts");
        buckets.put(3313201758L, "Ornaments");
    }

    static {
        damageTypes.put(0, "None");
        damageTypes.put(1, "Kinetic");
        damageTypes.put(2, "Arc");
        damageTypes.put(3, "Solar");
        damageTypes.put(4, "Void");
        damageTypes.put(9, "All");
    }

    static {
        classTypes.put(1L, "Hunter");
        classTypes.put(0L, "Titan");
        classTypes.put(2L, "Warlock");
    }

    static {
        tierTypes.put(1, "Common");
        tierTypes.put(2, "Uncommon");
        tierTypes.put(3, "Rare");
        tierTypes.put(4, "Legendary");
        tierTypes.put(5, "Exotic");
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

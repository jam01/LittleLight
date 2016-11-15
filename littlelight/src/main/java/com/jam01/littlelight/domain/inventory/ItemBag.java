package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 6/9/16.
 */
public abstract class ItemBag {
    public static final List<Long> ITEM_ORDER;

    static {
        ITEM_ORDER = new ArrayList<>();
        ITEM_ORDER.add(3284755031L);//"Subclass");
        ITEM_ORDER.add(1498876634L);//"Primary Weapons");
        ITEM_ORDER.add(2465295065L);//"Secondary Weapons");
        ITEM_ORDER.add(953998645L);//"Heavy Weapons");
        ITEM_ORDER.add(4023194814L);//"Ghosts");
        ITEM_ORDER.add(3448274439L);//"Helmets");
        ITEM_ORDER.add(3551918588L);//"Arms");
        ITEM_ORDER.add(14239492L);//"Chests");
        ITEM_ORDER.add(20886954L);//"Legs");
        ITEM_ORDER.add(1585787867L);//"Class Items");
        ITEM_ORDER.add(434908299L);//"Artifacts");
        ITEM_ORDER.add(2025709351L);//"Vehicles");
        ITEM_ORDER.add(3796357825L);//"Sparrow Horns");
        ITEM_ORDER.add(284967655L);//"Ships");
        ITEM_ORDER.add(2973005342L);//"Shaders");
        ITEM_ORDER.add(4274335291L);//"Emblems");
        ITEM_ORDER.add(3054419239L);//"Emotes");
        ITEM_ORDER.add(3865314626L);//"Materials");
        ITEM_ORDER.add(1469714392L);//"Consumables");
        ITEM_ORDER.add(2197472680L);//"Quests");
        ITEM_ORDER.add(375726501L);//"Mission Items");
        ITEM_ORDER.add(434908299L);//, "Artifacts");
        ITEM_ORDER.add(3313201758L);//9, "Ornaments");
        ITEM_ORDER.add(0L);//"All");
    }

    private final AccountId accountId;
    private final String id;
    protected Map<String, Item> itemMap;

    public ItemBag(Collection<Item> items, AccountId accountId, String id) {
        itemMap = new HashMap<>(items.size());
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }
        this.accountId = accountId;
        this.id = id;
    }

    public static List<Item> filterByItemType(List<Item> items, String itemType) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getItemType().equals(itemType)) {
                iterator.remove();
            }
        }
        return items;
    }

    public static List<Item> filterByItemSuperType(List<Item> items, String itemSuperType) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getItemSuperType().equals(itemSuperType)) {
                iterator.remove();
            }
        }
        return items;
    }

    public static List<Item> filterByItemSubType(List<Item> items, String itemSubType) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getItemSubType().equals(itemSubType)) {
                iterator.remove();
            }
        }
        return items;
    }

    public static List<Item> filterByMaxxedOnly(List<Item> items) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isGridComplete()) {
                iterator.remove();
            }
        }
        return items;
    }

    public static List<Item> filterByDamageType(List<Item> items, String damageType) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getDamageType().equals(damageType)) {
                iterator.remove();
            }
        }
        return items;
    }

    public String withId() {
        return id;
    }

    public boolean containsItem(String anItemId) {
        return itemMap.containsKey(anItemId);
    }

    protected void updateFrom(ItemBag newState) {
        itemMap.clear();
        itemMap.putAll(newState.itemMap);
    }

    public AccountId ofAccount() {
        return accountId;
    }

    public Item itemOfId(String anItemId) {
        return itemMap.get(anItemId);
    }

    protected void put(Item anItem) {
        itemMap.put(anItem.getItemId(), anItem);
    }

    protected Item take(String anItemId) {
        Item itemToReturn = itemMap.get(anItemId);
        itemMap.remove(anItemId);
        return itemToReturn;
    }

    protected Item get(String anItemId) {
        return itemMap.get(anItemId);
    }

    public Collection<Item> items() {
        return Collections.unmodifiableCollection(itemMap.values());
//        return itemMap.values();
    }

    public List<Item> orderedItems() {
        List<Item> toReturn = new ArrayList<>(itemMap.values());
        Collections.sort(toReturn, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return ITEM_ORDER.indexOf(o1.getItemTypeHash()) - ITEM_ORDER.indexOf(o2.getItemTypeHash());
            }
        });

//        return filterByItemType(filterByItemSubType(filterByMaxxedOnly(toReturn), "Shotgun"), "Secondary Weapons");
        return toReturn;
    }
}

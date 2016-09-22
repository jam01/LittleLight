package com.jam01.littlelight.adapter.android.rxlegacy.data;

import com.jam01.littlelight.adapter.android.legacy.DataStructures.InventoryItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jam01 on 5/21/16.
 */
public class JsonHelper {
    private static final boolean TO_VAULT = true;
    private static final boolean TO_CHARACTER = false;

    public static JSONObject prepareTransferRequest(InventoryItem item, boolean direction, String characterId) {
        JSONObject tmp = new JSONObject();
        try {
            if (characterId == null)
                characterId = item.getCharacterId();
            tmp.put("characterId", characterId);
            tmp.put("itemId", item.getItemInstanceId());
            tmp.put("itemReferenceHash", item.getItemHash());
            tmp.put("membershipType", item.getMembershipType());
            tmp.put("stackSize", item.getStackSize());
            tmp.put("transferToVault", direction);

        } catch (JSONException e) {
            e.printStackTrace();
            tmp = null;
        }
        return tmp;
    }
}

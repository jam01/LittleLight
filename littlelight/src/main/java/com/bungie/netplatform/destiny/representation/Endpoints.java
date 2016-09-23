package com.bungie.netplatform.destiny.representation;

/**
 * Created by jam01 on 3/30/15.
 */
public class Endpoints {
    public static final String BASE_URL = "https://www.bungie.net/";
    public static final String BASE_URL_NSECURE = "http://www.bungie.net/";
    public static final String PLATFORM_URI = "Platform/Destiny/";
    public static final String LANGUAGE_URI = "en/";
    public static final String SIGN_IN_URI = BASE_URL + LANGUAGE_URI + "User/SignIn/";
    public static final String PSN_AUTH_URL = SIGN_IN_URI + "Psnid?bru=%2f";
    public static final String XBOX_AUTH_URL = SIGN_IN_URI + "Xuid?bru=%2f";
    public static final String GET_MEMBERSHIP_ID_BY_DISPLAY_NAME_URL = BASE_URL + PLATFORM_URI + "SearchDestinyPlayer/%s/%s/";
    public static final String ACCOUNT_URI = "Account/";
    public static final String GET_ACCOUNT_INFO_URL = BASE_URL + PLATFORM_URI + "%s/" + ACCOUNT_URI + "%s/";
    public static final String RETROFIT_GET_ACCOUNT_INFO_URL = BASE_URL + PLATFORM_URI + "{membershipType}" + ACCOUNT_URI + "{membershipId}";
    public static final String CHARACTER_URI = "Character/";
    public static final String GET_CHARACTER_COMPLETE_URL = BASE_URL + PLATFORM_URI + "%s/" + ACCOUNT_URI + "%s/" + CHARACTER_URI + "%s/Complete/";
    public static final String GET_INVENTORY_URL = BASE_URL + PLATFORM_URI + "%s/" + ACCOUNT_URI + "%s/" + CHARACTER_URI + "%s/Inventory/";
    public static final String GET_CHARACTER_URL = BASE_URL + PLATFORM_URI + "%s/" + ACCOUNT_URI + "%s/" + CHARACTER_URI + "%s/";
    public static final String LEGEND_URL = BASE_URL + LANGUAGE_URI + "/Legend/";
    public static final String EQUIP_ITEM_URL = BASE_URL + PLATFORM_URI + "EquipItem/";
    public static final String TRANSFER_ITEM_URL = BASE_URL + PLATFORM_URI + "TransferItem/";
    public static final String VAULT_URL = BASE_URL + PLATFORM_URI + "%s/MyAccount/Vault/";
    public static final String ADVISORS_URL = BASE_URL + PLATFORM_URI + "%s/MyAccount/Character/%s/Advisors/";
    public static final String GET_MEMBERSHIP_URL = BASE_URL + "platform/User/GetMembershipIds/";
    public static final String MANIFEST_URL = BASE_URL + PLATFORM_URI + "Manifest/";
    public static final String SIGN_OUT_URL = BASE_URL + LANGUAGE_URI + "User/SignOut";
}

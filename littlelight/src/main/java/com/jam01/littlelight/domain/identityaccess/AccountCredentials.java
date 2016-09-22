package com.jam01.littlelight.domain.identityaccess;

/**
 * Created by jam01 on 7/25/16.
 */
public class AccountCredentials {
    private final String bungled;
    private final String bungleatk;

    public AccountCredentials(String bungled, String bungleatk) {
        this.bungled = bungled;
        this.bungleatk = bungleatk;
    }

    public String asCookieVal() {
        return "bungled=" + bungled + "; bungleatk=" + bungleatk;
    }

    public String xcsrf() {
        return bungled.substring(8);
    }
}

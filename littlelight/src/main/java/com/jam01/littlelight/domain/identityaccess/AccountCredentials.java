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

    public static AccountCredentials instanceFrom(String[] cookies) {
        String bungled = null, bungleatk = null;
        for (String ar1 : cookies) {
            if (ar1.contains("bungled=")) {
                bungled = ar1.substring(8);
            } else if (ar1.contains("bungleatk=")) {
                bungleatk = ar1.substring(10);
            }
        }
        return new AccountCredentials(bungled, bungleatk);
    }

    public String asCookieVal() {
        return "bungled=" + bungled + "bungleatk=" + bungleatk;
    }

    public String xcsrf() {
        return bungled.substring(0, bungled.length() - 1);
    }
}

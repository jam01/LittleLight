package com.jam01.littlelight.adapter.android.utils;

/**
 * Created by jam01 on 11/16/16.
 */

public class IllegalNetworkStateException extends IllegalStateException {

    public IllegalNetworkStateException() {
    }

    public IllegalNetworkStateException(String s) {
        super(s);
    }

    public IllegalNetworkStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNetworkStateException(Throwable cause) {
        super(cause);
    }
}

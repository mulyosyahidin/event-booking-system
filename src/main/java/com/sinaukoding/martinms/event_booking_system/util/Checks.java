package com.sinaukoding.martinms.event_booking_system.util;

public class Checks {

    public static void isTrue(boolean param, String message) {
        if (!param) throw newE(message);
    }

    public static RuntimeException newE(String message) {
        return new RuntimeException(message);
    }

}

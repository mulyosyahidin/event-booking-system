package com.sinaukoding.martinms.event_booking_system.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Util {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public static String generateBookingId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        StringBuilder randomLetters = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            randomLetters.append(ALPHABET.charAt(index));
        }

        int randomNumbers = 1000 + RANDOM.nextInt(9000);

        return datePart + randomLetters + randomNumbers;
    }

    public static String cutString(String str, int max) {
        if (str.length() > max) {
            return str.substring(0, max);
        } else {
            return str;
        }
    }

    public static String maskString(String str) {
        return maskString(str, 3);
    }

    public static String maskString(String str, int max) {
        if (str == null) {
            return null;
        }

        if (max < 0) {
            throw new IllegalArgumentException("max tidak boleh negatif");
        }

        if (str.length() <= max) {
            return str;
        }

        String prefix = str.substring(0, max);
        String mask = "*".repeat(str.length() - max);
        return prefix + mask;
    }

}

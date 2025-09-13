package com.sinaukoding.martinms.event_booking_system.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    public static String formatLocalDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.of("id", "ID")));
    }

    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.of("id", "ID"))
        );
    }

}

package com.sinaukoding.martinms.event_booking_system.model.enums;

import lombok.Getter;

@Getter
public enum BookingStatus {

    PENDING("Pending"),
    BATAL("Batal"),
    BERHASIL("Berhasil");

    private final String label;

    BookingStatus(String label) {
        this.label = label;
    }

}

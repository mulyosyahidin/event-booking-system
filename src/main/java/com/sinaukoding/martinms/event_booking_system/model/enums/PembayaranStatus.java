package com.sinaukoding.martinms.event_booking_system.model.enums;

import lombok.Getter;

@Getter
public enum PembayaranStatus {

    GAGAL("Gagal"),
    PENDING("Pending"),
    BATAL("Batal"),
    BERHASIL("Berhasil"),
    KADALURSA("Kadalursa"),
    LAINNYA("Lainnya"),
    ;

    private final String label;

    PembayaranStatus(String label) {
        this.label = label;
    }

}

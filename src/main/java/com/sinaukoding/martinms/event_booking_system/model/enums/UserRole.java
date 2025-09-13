package com.sinaukoding.martinms.event_booking_system.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    USER("User");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

}

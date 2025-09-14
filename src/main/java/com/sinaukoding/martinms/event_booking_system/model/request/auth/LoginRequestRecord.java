package com.sinaukoding.martinms.event_booking_system.model.request.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecord(
        @NotBlank(message = "Username tidak boleh kosong")
        String username,

        @NotBlank(message = "Password tidak boleh kosong")
        String password
) {
}

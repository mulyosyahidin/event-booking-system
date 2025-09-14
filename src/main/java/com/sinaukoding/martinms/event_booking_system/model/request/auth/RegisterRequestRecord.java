package com.sinaukoding.martinms.event_booking_system.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestRecord(
        @NotBlank(message = "Nama tidak boleh kosong")
        String nama,

        @NotBlank(message = "Email tidak boleh kosong")
        @Email(message = "Email harus valid")
        String email,

        @NotBlank(message = "Username tidak boleh kosong")
        String username,

        @NotBlank(message = "Password tidak boleh kosong")
        String password,

        String phoneNumber
) {
}

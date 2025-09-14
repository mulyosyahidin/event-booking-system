package com.sinaukoding.martinms.event_booking_system.model.request.admin.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestRecord(
        @NotBlank(message = "Nama tidak boleh kosong")
        String nama,

        @NotBlank(message = "Email tidak boleh kosong")
        @Email(message = "Email harus valid")
        String email,

        @NotBlank(message = "Username tidak boleh kosong")
        String username,

        String password,

        String phoneNumber
) {
}

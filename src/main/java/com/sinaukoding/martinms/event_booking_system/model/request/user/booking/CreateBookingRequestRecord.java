package com.sinaukoding.martinms.event_booking_system.model.request.user.booking;

import jakarta.validation.constraints.NotBlank;

public record CreateBookingRequestRecord(
        @NotBlank(message = "ID Event tidak boleh kosong")
        String eventID
) {
}

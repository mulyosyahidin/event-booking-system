package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.model.enums.PembayaranStatus;

public interface IPembayaranService {
    Pembayaran save(Booking booking);

    void updatePaymentStatus(Pembayaran pembayaran, PembayaranStatus status);

    void updateExpiryTime(Pembayaran pembayaran, String expiryTime);

    void updatePaidAt(Pembayaran pembayaran, String settlementTime);
}

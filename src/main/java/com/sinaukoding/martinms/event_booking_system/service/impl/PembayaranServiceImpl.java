package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.mapper.PembayaranMapper;
import com.sinaukoding.martinms.event_booking_system.model.enums.PembayaranStatus;
import com.sinaukoding.martinms.event_booking_system.repository.PembayaranRepository;
import com.sinaukoding.martinms.event_booking_system.service.IPembayaranService;
import com.sinaukoding.martinms.event_booking_system.service.app.IMidtransService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PembayaranServiceImpl implements IPembayaranService {

    @Value("${payment.fee}")
    private Double paymentFee;

    private final PembayaranRepository pembayaranRepository;
    private final PembayaranMapper pembayaranMapper;
    private final IMidtransService midtransService;

    @Override
    public Pembayaran save(Booking booking) {
        Double totalBayar = booking.getHarga() + paymentFee;
        Pembayaran pembayaran = new Pembayaran();

        pembayaran.setBooking(booking);
        pembayaran.setHarga(booking.getHarga());
        pembayaran.setFee(paymentFee);
        pembayaran.setTotal(totalBayar);
        pembayaran.setStatus(PembayaranStatus.PENDING);
        pembayaran.setToken(midtransService.createTransaction(booking));

        return pembayaranRepository.save(pembayaran);
    }

    @Override
    public void updatePaymentStatus(Pembayaran pembayaran, PembayaranStatus status) {
        pembayaran.setStatus(status);
        pembayaranRepository.save(pembayaran);
    }

    @Override
    public void updateExpiryTime(Pembayaran pembayaran, String expiryTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(expiryTime, formatter);

        pembayaran.setExpiredAt(dateTime);
        pembayaranRepository.save(pembayaran);
    }

    @Override
    public void updatePaidAt(Pembayaran pembayaran, String settlementTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(settlementTime, formatter);

        pembayaran.setPaidAt(dateTime);
        pembayaranRepository.save(pembayaran);
    }

}

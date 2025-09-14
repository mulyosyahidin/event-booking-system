package com.sinaukoding.martinms.event_booking_system.controller;

import com.sinaukoding.martinms.event_booking_system.config.MidtransProperties;
import com.sinaukoding.martinms.event_booking_system.config.exception.UnauthorizedException;
import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import com.sinaukoding.martinms.event_booking_system.model.enums.PembayaranStatus;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import com.sinaukoding.martinms.event_booking_system.service.IPembayaranService;
import com.sinaukoding.martinms.event_booking_system.util.MidtransUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// https://github.com/mulyosyahidin/spring-boot-vocasia-microservices/blob/main/development/cloud/payment/src/main/java/com/vocasia/payment/controller/MidtransController.java
@RestController
@RequestMapping("midtrans")
@RequiredArgsConstructor
@Tag(name = "Midtrans Payment API")
public class MidtransController {

    private final Logger logger = LoggerFactory.getLogger(MidtransController.class);

    private final IBookingService bookingService;
    private final IEventService eventService;
    private final IPembayaranService pembayaranService;
    private final MidtransProperties midtransProperties;

    @PostMapping("callback")
    @Operation(
            summary = "Callback",
            description = "Menerima callback dari Midtrans"
    )
    public BaseResponse<?> callback(@RequestBody com.sinaukoding.martinms.event_booking_system.model.request.midtrans.MidtransCallbackRequest midtransCallbackRequest) {
        String serverKey = midtransProperties.getServerKey();
        String orderId = midtransCallbackRequest.getOrderId();
        String statusCode = midtransCallbackRequest.getStatusCode();
        String grossAmount = midtransCallbackRequest.getGrossAmount();

        String hashedKey = MidtransUtil.generateMidtransHash(orderId, statusCode, grossAmount, serverKey);

        if (!hashedKey.equals(midtransCallbackRequest.getSignatureKey())) {
            throw new UnauthorizedException("Akses tidak diizinkan");
        }

        Booking booking = bookingService.findByKodeBooking(midtransCallbackRequest.getOrderId());
        Pembayaran pembayaran = booking.getPembayaran();

        String transactionStatus = midtransCallbackRequest.getTransactionStatus();
        String paymentType = midtransCallbackRequest.getPaymentType();
        String fraudStatus = midtransCallbackRequest.getFraudStatus();

        PembayaranStatus paymentStatus = PembayaranStatus.LAINNYA;

        switch (transactionStatus) {
            case "capture":
                if ("credit_card".equals(paymentType)) {
                    if ("challenge".equals(fraudStatus)) {
                        paymentStatus = PembayaranStatus.PENDING;
                    } else {
                        paymentStatus = PembayaranStatus.BERHASIL;
                    }
                }
                break;
            case "settlement":
                paymentStatus = PembayaranStatus.BERHASIL;
                break;
            case "pending":
                paymentStatus = PembayaranStatus.PENDING;
                break;
            case "deny":
                paymentStatus = PembayaranStatus.GAGAL;
                break;
            case "expire":
                paymentStatus = PembayaranStatus.KADALURSA;
                break;
            case "cancel":
                paymentStatus = PembayaranStatus.BATAL;
                break;
            default:
                paymentStatus = PembayaranStatus.LAINNYA;
                break;
        }

        pembayaranService.updatePaymentStatus(pembayaran, paymentStatus);
        pembayaranService.updateExpiryTime(pembayaran, midtransCallbackRequest.getExpiryTime());

        if (transactionStatus.equals("settlement")) {
            bookingService.updateStatus(booking, BookingStatus.BERHASIL);
            pembayaranService.updatePaidAt(pembayaran, midtransCallbackRequest.getSettlementTime());
        }

        if (transactionStatus.equals("expire") || transactionStatus.equals("cancel") || transactionStatus.equals("deny")) {
            eventService.tambahSisaKuota(booking.getEvent());
            bookingService.updateStatus(booking, BookingStatus.BATAL);
        }

        return BaseResponse.ok("Data pembayaran berhasil diproses", null);
    }

}

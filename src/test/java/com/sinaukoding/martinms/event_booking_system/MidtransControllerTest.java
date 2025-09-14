package com.sinaukoding.martinms.event_booking_system;

import com.sinaukoding.martinms.event_booking_system.config.MidtransProperties;
import com.sinaukoding.martinms.event_booking_system.config.exception.UnauthorizedException;
import com.sinaukoding.martinms.event_booking_system.controller.MidtransController;
import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import com.sinaukoding.martinms.event_booking_system.model.enums.PembayaranStatus;
import com.sinaukoding.martinms.event_booking_system.model.request.midtrans.MidtransCallbackRequest;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import com.sinaukoding.martinms.event_booking_system.service.IPembayaranService;
import com.sinaukoding.martinms.event_booking_system.util.MidtransUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MidtransControllerTest {

    @Mock
    private IBookingService bookingService;

    @Mock
    private IEventService eventService;

    @Mock
    private IPembayaranService pembayaranService;

    @Mock
    private MidtransProperties midtransProperties;

    @InjectMocks
    private MidtransController midtransController;

    private Booking booking;
    private Pembayaran pembayaran;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        event = new Event();
        event.setId("EVT123");

        pembayaran = new Pembayaran();
        pembayaran.setId("PAY123");

        booking = new Booking();
        booking.setId("BOOKING-1234");
        booking.setKodeBooking("BOOKING-1234");
        booking.setEvent(event);
        booking.setPembayaran(pembayaran);

        when(midtransProperties.getServerKey()).thenReturn("server-key");
    }

    @Test
    void callback_Settlement_Success() {
        MidtransCallbackRequest req = new MidtransCallbackRequest();
        req.setOrderId("BOOKING-1234");
        req.setStatusCode("200");
        req.setGrossAmount("100000");
        req.setTransactionStatus("settlement");
        req.setPaymentType("bank_transfer");
        req.setFraudStatus("accept");
        req.setSignatureKey(MidtransUtil.generateMidtransHash(
                "BOOKING-1234", "200", "100000", "server-key"
        ));
        req.setSettlementTime("2025-09-14 10:00:00");
        req.setExpiryTime("2025-09-14 12:00:00");

        when(bookingService.findByKodeBooking("BOOKING-1234")).thenReturn(booking);

        BaseResponse<?> response = midtransController.callback(req);

        assertNotNull(response);
        assertEquals("Data pembayaran berhasil diproses", response.getMessage());

        verify(pembayaranService).updatePaymentStatus(pembayaran, PembayaranStatus.BERHASIL);
        verify(pembayaranService).updateExpiryTime(pembayaran, req.getExpiryTime());
        verify(bookingService).updateStatus(booking, BookingStatus.BERHASIL);
        verify(pembayaranService).updatePaidAt(pembayaran, req.getSettlementTime());
        verifyNoInteractions(eventService);
    }

    @Test
    void callback_Expire_BookingCanceled() {
        MidtransCallbackRequest req = new MidtransCallbackRequest();
        req.setOrderId("BOOKING-1234");
        req.setStatusCode("202");
        req.setGrossAmount("50000");
        req.setTransactionStatus("expire");
        req.setPaymentType("bank_transfer");
        req.setFraudStatus("accept");
        req.setSignatureKey(MidtransUtil.generateMidtransHash(
                "BOOKING-1234", "202", "50000", "server-key"
        ));
        req.setExpiryTime("2025-09-14 12:00:00");

        when(bookingService.findByKodeBooking("BOOKING-1234")).thenReturn(booking);

        BaseResponse<?> response = midtransController.callback(req);

        assertNotNull(response);
        assertEquals("Data pembayaran berhasil diproses", response.getMessage());

        verify(pembayaranService).updatePaymentStatus(pembayaran, PembayaranStatus.KADALURSA);
        verify(pembayaranService).updateExpiryTime(pembayaran, req.getExpiryTime());
        verify(eventService).tambahSisaKuota(event);
        verify(bookingService).updateStatus(booking, BookingStatus.BATAL);
    }

    @Test
    void callback_InvalidSignature_ThrowsUnauthorized() {
        MidtransCallbackRequest req = new MidtransCallbackRequest();
        req.setOrderId("BOOKING-1234");
        req.setStatusCode("201");
        req.setGrossAmount("100000");
        req.setTransactionStatus("pending");
        req.setSignatureKey("invalid-signature");

        when(bookingService.findByKodeBooking("BOOKING-1234")).thenReturn(booking);

        assertThrows(UnauthorizedException.class, () -> midtransController.callback(req));
    }

}

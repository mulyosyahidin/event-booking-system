package com.sinaukoding.martinms.event_booking_system.admin.booking;

import com.sinaukoding.martinms.event_booking_system.controller.admin.BookingController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.booking.AdminBookingFilterRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingControllerTest {

    @Mock
    private IBookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private SimpleMap bookingData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingData = new SimpleMap();
        bookingData.put("id", "B001");
        bookingData.put("kodeBooking", "BOOK123");
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        AdminBookingFilterRecord filter = new AdminBookingFilterRecord("PENDING");
        Page<SimpleMap> page = new PageImpl<>(List.of(bookingData));

        when(bookingService.findAll(filter, pageable)).thenReturn(page);

        BaseResponse<?> response = bookingController.findAll(pageable, filter);

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data booking", response.getMessage());
        assertNotNull(response.getData());

        verify(bookingService).findAll(filter, pageable);
    }

    @Test
    void findById_Success() {
        when(bookingService.findById("B001")).thenReturn(bookingData);

        BaseResponse<?> response = bookingController.findById("B001");

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data booking", response.getMessage());
        assertEquals("BOOK123", ((SimpleMap) response.getData()).get("kodeBooking"));

        verify(bookingService).findById("B001");
    }
}

package com.sinaukoding.martinms.event_booking_system.user.booking;

import com.sinaukoding.martinms.event_booking_system.controller.user.BookingController;
import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.mapper.BookingMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.user.booking.CreateBookingRequestRecord;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private IBookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingController bookingController;

    private UserDetails userDetails;
    private SimpleMap bookingData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetails = User.withUsername("martin").password("secret").authorities("USER").build();
        bookingData = new SimpleMap();
        bookingData.put("id", "1");
        bookingData.put("kodeBooking", "BOOK123");
    }

    @Test
    void findAll_Success() {
        Page<SimpleMap> page = new PageImpl<>(List.of(bookingData));

        when(bookingService.findAllByUser(eq("martin"), any(PageRequest.class))).thenReturn(page);

        BaseResponse<?> response = bookingController.findAll(userDetails, PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data booking", response.getMessage());
        assertNotNull(response.getData());

        verify(bookingService).findAllByUser(eq("martin"), any(PageRequest.class));
    }

    @Test
    void findById_Success() {
        when(bookingService.findByIdAndUsername("martin", "1")).thenReturn(bookingData);

        BaseResponse<?> response = bookingController.findById(userDetails, "1");

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data booking", response.getMessage());
        assertEquals("BOOK123", ((SimpleMap) response.getData()).get("kodeBooking"));

        verify(bookingService).findByIdAndUsername("martin", "1");
    }

    @Test
    void create_Success() {
        CreateBookingRequestRecord request = new CreateBookingRequestRecord("BOOKING-EVENT-1234");

        when(bookingService.create("martin", request)).thenReturn(bookingData);

        BaseResponse<?> response = bookingController.create(userDetails, request);

        assertNotNull(response);
        assertEquals("Berhasil membuat booking baru", response.getMessage());
        assertEquals("BOOK123", ((SimpleMap) response.getData()).get("kodeBooking"));

        verify(bookingService).create("martin", request);
    }

    @Test
    void data_Success() {
        Booking mockBooking = new Booking();
        mockBooking.setId("1");
        mockBooking.setKodeBooking("BOOK123");

        when(bookingService.findByKodeBooking("BOOK123")).thenReturn(mockBooking);
        when(bookingMapper.entityToSimpleMap(mockBooking)).thenReturn(bookingData); // 👈 tambahan

        BaseResponse<?> response = bookingController.data("BOOK123");

        assertNotNull(response);
        assertEquals(
                "Seharusnya redirect dari Midtrans dialihkan ke website front-end aplikasi, tapi karena tidak ada front-end nya, kesini saja dulu...",
                response.getMessage()
        );
        assertNotNull(response.getData());
        assertEquals("BOOK123", ((SimpleMap) response.getData()).get("kodeBooking"));

        verify(bookingService).findByKodeBooking("BOOK123");
        verify(bookingMapper).entityToSimpleMap(mockBooking);
    }

}

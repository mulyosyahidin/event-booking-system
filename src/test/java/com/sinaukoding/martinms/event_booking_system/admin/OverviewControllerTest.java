package com.sinaukoding.martinms.event_booking_system.admin;

import com.sinaukoding.martinms.event_booking_system.controller.admin.OverviewController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OverviewControllerTest {

    @Mock
    private IBookingService bookingService;

    @Mock
    private IEventService eventService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private OverviewController overviewController;

    private SimpleMap userOverview;
    private SimpleMap eventOverview;
    private SimpleMap bookingOverview;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userOverview = new SimpleMap();
        userOverview.put("total", 10);

        eventOverview = new SimpleMap();
        eventOverview.put("total", 5);

        bookingOverview = new SimpleMap();
        bookingOverview.put("total", 20);
    }

    @Test
    void overview_Success() {
        when(userService.getOverview()).thenReturn(userOverview);
        when(eventService.getOverview()).thenReturn(eventOverview);
        when(bookingService.getOverview()).thenReturn(bookingOverview);

        BaseResponse<?> response = overviewController.overview();

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan ringkasan aplikasi", response.getMessage());
        assertNotNull(response.getData());

        SimpleMap data = (SimpleMap) response.getData();
        assertEquals(userOverview, data.get("user"));
        assertEquals(eventOverview, data.get("event"));
        assertEquals(bookingOverview, data.get("booking"));

        verify(userService).getOverview();
        verify(eventService).getOverview();
        verify(bookingService).getOverview();
    }
}

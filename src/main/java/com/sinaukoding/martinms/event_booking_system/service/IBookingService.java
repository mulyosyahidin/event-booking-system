package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.booking.AdminBookingFilterRecord;
import com.sinaukoding.martinms.event_booking_system.model.request.user.booking.CreateBookingRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService {
    Page<SimpleMap> findAllByUser(String username, Pageable pageable);

    SimpleMap findByIdAndUsername(String username, String id);

    SimpleMap create(String username, CreateBookingRequestRecord request);

    Booking findByKodeBooking(String orderId);

    void updateStatus(Booking booking, BookingStatus bookingStatus);

    Page<SimpleMap> findAllByEventId(String id, Pageable pageable);

    Page<SimpleMap> findAll(AdminBookingFilterRecord filterRecord, Pageable pageable);

    SimpleMap findById(String id);

    SimpleMap getOverview();
}

package com.sinaukoding.martinms.event_booking_system.repository;

import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String>, JpaSpecificationExecutor<Booking> {
    boolean existsByUserAndEvent(User user, Event event);

    Optional<Booking> findByIdAndUser(String id, User user);

    Page<Booking> findAllByUser(User user, Pageable pageable);

    Optional<Booking> findByKodeBooking(String kodeBooking);

    Page<Booking> findAllByEvent(Event event, Pageable pageable);

    int countByStatus(BookingStatus bookingStatus);
}

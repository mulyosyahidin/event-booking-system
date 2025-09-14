package com.sinaukoding.martinms.event_booking_system.repository;

import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String>, JpaSpecificationExecutor<Event> {
    boolean existsByNama(String nama);

    Optional<Event> findByIdAndStatus(String id, Status status);

    int countByStatus(Status status);

    int countByWaktuSelesaiBefore(LocalDateTime now);

    int countByWaktuSelesaiAfter(LocalDateTime now);
}

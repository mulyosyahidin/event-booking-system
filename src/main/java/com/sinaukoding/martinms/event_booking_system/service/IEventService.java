package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.event.EventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEventService {
    SimpleMap create(EventRequest request);

    SimpleMap findById(String id);

    Page<SimpleMap> findAll(Pageable pageable);

    SimpleMap update(String id, EventRequest request);

    void destroy(String id);
}

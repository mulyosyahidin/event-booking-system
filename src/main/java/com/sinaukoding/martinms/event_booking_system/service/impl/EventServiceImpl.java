package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.repository.EventRepository;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;

}

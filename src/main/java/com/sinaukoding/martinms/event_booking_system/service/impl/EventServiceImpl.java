package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.builder.CustomBuilder;
import com.sinaukoding.martinms.event_booking_system.builder.CustomSpecification;
import com.sinaukoding.martinms.event_booking_system.builder.SearchCriteria;
import com.sinaukoding.martinms.event_booking_system.config.exception.ConflictResourceException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ValidationErrorException;
import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.mapper.EventMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.AppPage;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.KategoriEvent;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.CreateEventRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.UpdateEventRequestRecord;
import com.sinaukoding.martinms.event_booking_system.repository.EventRepository;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final IValidatorService validatorService;

    @Override
    public SimpleMap create(CreateEventRequestRecord request) {
        validatorService.validator(request);

        if (eventRepository.existsByNama(request.nama())) {
            throw new ConflictResourceException("Nama event sudah ada, gunakan nama lain");
        }

        try {
            KategoriEvent.valueOf(request.kategori());
        } catch (IllegalArgumentException e) {
            throw new ValidationErrorException("Kategori tidak valid");
        }

        Event event = eventRepository.save(eventMapper.requestToEntity(request, Status.AKTIF));
        event.setSisaKuota(request.kuota());

        return eventMapper.entityToSimpleMap(event);
    }

    @Override
    public SimpleMap findById(String id) {
        Event event = eventRepository.findByIdAndStatus(id, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("Event tidak ditemukan"));

        return eventMapper.entityToSimpleMap(event);
    }

    @Override
    public Page<SimpleMap> findAll(Pageable pageable) {
        CustomBuilder<Event> builder = new CustomBuilder<>();

        builder.with(SearchCriteria.of("status", CustomSpecification.OPERATION_EQUAL, Status.AKTIF));

        Page<Event> events = eventRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = events.stream().map(eventMapper::entityToSimpleMap).toList();

        return AppPage.create(listData, pageable, events.getTotalElements());
    }

    @Override
    public SimpleMap update(String id, UpdateEventRequestRecord request) {
        validatorService.validator(request);

        Event event = eventRepository.findByIdAndStatus(id, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("Event tidak ditemukan"));

        if (!event.getNama().equals(request.nama()) &&
                eventRepository.existsByNama(request.nama())) {
            throw new ConflictResourceException("Nama event sudah ada, gunakan nama lain");
        }

        try {
            KategoriEvent.valueOf(request.kategori());
        } catch (IllegalArgumentException e) {
            throw new ValidationErrorException("Kategori tidak valid");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        event.setNama(request.nama());
        event.setDeskripsi(request.deskripsi());
        event.setGambar(request.gambar());
        event.setKategori(KategoriEvent.valueOf(request.kategori()));
        event.setLokasi(request.lokasi());
        event.setWaktuMulai(LocalDateTime.parse(request.waktuMulai(), formatter));
        event.setWaktuSelesai(LocalDateTime.parse(request.waktuSelesai(), formatter));
        event.setHarga(request.harga());

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.entityToSimpleMap(updatedEvent);
    }

    @Override
    public void destroy(String id) {
        Event event = eventRepository.findByIdAndStatus(id, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("Event tidak ditemukan"));

        event.setStatus(Status.TIDAK_AKTIF);
        eventRepository.save(event);
    }

    @Override
    public void tambahSisaKuota(Event event) {
        event.setSisaKuota(event.getSisaKuota() + 1);
        eventRepository.save(event);
    }

}

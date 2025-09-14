package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.config.exception.ConflictResourceException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.BookingMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.AppPage;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.request.user.booking.CreateBookingRequestRecord;
import com.sinaukoding.martinms.event_booking_system.repository.BookingRepository;
import com.sinaukoding.martinms.event_booking_system.repository.EventRepository;
import com.sinaukoding.martinms.event_booking_system.repository.UserRepository;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IPembayaranService;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final IPembayaranService pembayaranService;
    private final IValidatorService validatorService;

    @Override
    public Page<SimpleMap> findAllByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        Page<Booking> bookings = bookingRepository.findAllByUser(user, pageable);
        List<SimpleMap> listData = bookings.stream().map(bookingMapper::entityToSimpleMap).toList();

        return AppPage.create(listData, pageable, bookings.getTotalElements());
    }

    @Override
    public SimpleMap findByIdAndUsername(String username, String id) {
        User user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        // sertakan user dalam query supaya booking yang ditampilkan benar milik user tersebut
        Booking booking = bookingRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Data booking tidak ditemukan"));

        return bookingMapper.entityToSimpleMap(booking);
    }

    @Override
    public SimpleMap create(String username, CreateBookingRequestRecord request) {
        validatorService.validator(request);

        User user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        Event event = eventRepository.findByIdAndStatus(request.eventID(), Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("Event tidak ditemukan"));

        // cek apakah user ini sudah pernah booking event ini atau belum
        if (bookingRepository.existsByUserAndEvent(user, event)) {
            throw new ConflictResourceException("Anda sudah pernah melakukan booking pada event ini");
        }

        if (event.getSisaKuota() <= 0) {
            throw new ConflictResourceException("Kuota sudah habis");
        }

        event.setSisaKuota(event.getSisaKuota() - 1);
        eventRepository.save(event);

        Booking booking = bookingMapper.requestToEntity(request, user, event);
        bookingRepository.save(booking);

        Pembayaran pembayaran = pembayaranService.save(booking);
        booking.setPembayaran(pembayaran);

        return bookingMapper.entityToSimpleMap(booking);
    }

    @Override
    public Booking findByKodeBooking(String kodeBooking) {
        return bookingRepository.findByKodeBooking(kodeBooking)
                .orElseThrow(() -> new ResourceNotFoundException("Booking tidak ditemukan"));
    }

    @Override
    public void updateStatus(Booking booking, BookingStatus bookingStatus) {
        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);
    }

}

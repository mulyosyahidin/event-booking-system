package com.sinaukoding.martinms.event_booking_system.mapper;

import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import com.sinaukoding.martinms.event_booking_system.model.request.user.booking.CreateBookingRequestRecord;
import com.sinaukoding.martinms.event_booking_system.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingMapper {

    private final PembayaranMapper pembayaranMapper;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public SimpleMap entityToSimpleMap(Booking booking) {
        SimpleMap simpleMap = new SimpleMap();

        simpleMap.put("id", booking.getId());
        simpleMap.put("kode_booking", booking.getKodeBooking());
        simpleMap.put("harga", booking.getHarga());
        simpleMap.put("status", booking.getStatus());
        simpleMap.put("user", userMapper.entityToSimpleMap(booking.getUser()));
        simpleMap.put("event", eventMapper.entityToSimpleMap(booking.getEvent()));
        simpleMap.put("pembayaran", pembayaranMapper.entityToSimpleMap(booking.getPembayaran()));

        return simpleMap;
    }

    public Booking requestToEntity(CreateBookingRequestRecord request, User user, Event event) {
        return Booking.builder()
                .user(user)
                .event(event)
                .harga(event.getHarga())
                .status(BookingStatus.PENDING)
                .kodeBooking(Util.generateBookingId())
                .build();
    }
}

package com.sinaukoding.martinms.event_booking_system.mapper;

import com.sinaukoding.martinms.event_booking_system.entity.Event;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.KategoriEvent;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.EventRequest;
import com.sinaukoding.martinms.event_booking_system.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EventMapper {

    public Event requestToEntity(EventRequest request, Status status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime waktuMulai = LocalDateTime.parse(request.waktuMulai(), formatter);
        LocalDateTime waktuSelesai = LocalDateTime.parse(request.waktuSelesai(), formatter);

        return Event.builder()
                .nama(request.nama())
                .gambar(request.gambar())
                .deskripsi(request.deskripsi())
                .lokasi(request.lokasi())
                .kategori(KategoriEvent.valueOf(request.kategori()))
                .harga(request.harga())
                .kuota(request.kuota())
                .waktuMulai(waktuMulai)
                .waktuSelesai(waktuSelesai)
                .status(status)
                .build();
    }

    public SimpleMap entityToSimpleMap(Event event) {
        SimpleMap simpleMap = new SimpleMap();

        simpleMap.add("id", event.getId());
        simpleMap.add("gambar", event.getGambar());
        simpleMap.add("nama", event.getNama());
        simpleMap.add("deskripsi", event.getDeskripsi());
        simpleMap.add("lokasi", event.getLokasi());
        simpleMap.add("kategori", event.getKategori());
        simpleMap.add("harga", event.getHarga());
        simpleMap.add("kuota", event.getKuota());
        simpleMap.add("waktuMulai", DateUtil.formatLocalDateTimeToString(event.getWaktuMulai()));
        simpleMap.add("waktuSelesai", DateUtil.formatLocalDateTimeToString(event.getWaktuSelesai()));

        return simpleMap;
    }

}

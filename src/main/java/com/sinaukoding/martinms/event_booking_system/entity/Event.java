package com.sinaukoding.martinms.event_booking_system.entity;

import com.sinaukoding.martinms.event_booking_system.model.enums.KategoriEvent;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_event", indexes = {
        @Index(name = "idx_event_created_date", columnList = "createdDate"),
        @Index(name = "idx_event_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_event_nama", columnList = "nama"),
        @Index(name = "idx_event_kategori", columnList = "kategori"),
        @Index(name = "idx_event_status", columnList = "status")
})
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 150)
    private String nama;

    @Column(length = 500)
    private String deskripsi;

    @Column(length = 255)
    private String gambar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private KategoriEvent kategori;

    @Column(length = 255)
    private String lokasi;

    @Column(nullable = false)
    private LocalDateTime waktuMulai;

    @Column(nullable = false)
    private LocalDateTime waktuSelesai;

    @Column(nullable = false)
    private Integer kuota;

    @Column(nullable = false)
    private Double harga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

}

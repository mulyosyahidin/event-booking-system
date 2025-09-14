package com.sinaukoding.martinms.event_booking_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sinaukoding.martinms.event_booking_system.model.enums.PembayaranStatus;
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
@Table(name = "m_pembayaran", indexes = {
        @Index(name = "idx_pembayaran_created_date", columnList = "createdDate"),
        @Index(name = "idx_pembayaran_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_pembayaran_status", columnList = "status")
})
public class Pembayaran extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = true)
    @JsonBackReference
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PembayaranStatus status;

    String token;

    Double harga;
    Double fee;
    Double total;

    LocalDateTime expiredAt;
    LocalDateTime paidAt;

}

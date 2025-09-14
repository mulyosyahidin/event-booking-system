package com.sinaukoding.martinms.event_booking_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinaukoding.martinms.event_booking_system.model.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_booking", indexes = {
        @Index(name = "idx_booking_created_date", columnList = "createdDate"),
        @Index(name = "idx_booking_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_booking_status", columnList = "status")
})
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @Column(nullable = false, unique = true)
    String kodeBooking;

    @Column(nullable = false)
    Double harga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BookingStatus status;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Pembayaran pembayaran;

}

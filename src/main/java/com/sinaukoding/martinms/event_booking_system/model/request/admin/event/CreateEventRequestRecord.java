package com.sinaukoding.martinms.event_booking_system.model.request.admin.event;

import jakarta.validation.constraints.*;

public record CreateEventRequestRecord(

        @NotBlank(message = "Nama event tidak boleh kosong")
        @Size(max = 150, message = "Nama event maksimal 150 karakter")
        String nama,

        @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
        String deskripsi,

        @Size(max = 255, message = "Gambar maksimal 255 karakter")
        String gambar,

        @NotNull(message = "Kategori event tidak boleh kosong")
        String kategori,

        @Size(max = 255, message = "Lokasi maksimal 255 karakter")
        String lokasi,

        @NotNull(message = "Waktu mulai tidak boleh kosong")
        String waktuMulai,

        @NotNull(message = "Waktu selesai tidak boleh kosong")
        String waktuSelesai,

        @NotNull(message = "Kuota tidak boleh kosong")
        @Positive(message = "Kuota harus lebih dari 0")
        Integer kuota,

        @NotNull(message = "Harga tidak boleh kosong")
        @PositiveOrZero(message = "Harga tidak boleh negatif")
        Double harga
) {
}

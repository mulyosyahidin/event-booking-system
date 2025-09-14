package com.sinaukoding.martinms.event_booking_system.controller.admin;

import com.sinaukoding.martinms.event_booking_system.model.request.admin.booking.AdminBookingFilterRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("AdminBookingController")
@RequestMapping("admin/bookings")
@RequiredArgsConstructor
@Tag(name = "[Admin] Bookings")
public class BookingController {

    private final IBookingService bookingService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Semua data booking",
            description = "Mendapatkan semua data booking user"
    )
    public BaseResponse<?> findAll(
            @PageableDefault(direction = Sort.Direction.ASC, sort = "modifiedDate")
            @Parameter(hidden = true) Pageable pageable,
            @ModelAttribute AdminBookingFilterRecord filter) {
        return BaseResponse.ok("Berhasil mendapatkan data booking", bookingService.findAll(filter, pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Data booking",
            description = "Mendapatkan data booking berdasarkan ID"
    )
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data booking", bookingService.findById(id));
    }

}

package com.sinaukoding.martinms.event_booking_system.controller.user;

import com.sinaukoding.martinms.event_booking_system.model.request.user.booking.CreateBookingRequestRecord;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("UserBookingController")
@RequestMapping("users/bookings")
@RequiredArgsConstructor
@Tag(name = "[Users] Bookings")
public class BookingController {

    private final IBookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Semua data booking",
            description = "Mendapatkan semua data booking user yang sedang login"
    )
    public BaseResponse<?> findAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(direction = Sort.Direction.ASC, sort = "modifiedDate")
            @Parameter(hidden = true) Pageable pageable) {
        return BaseResponse.ok("Berhasil mendapatkan data booking", bookingService.findAllByUser(userDetails.getUsername(), pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Data booking",
            description = "Mendapatkan data booking berdasarkan ID"
    )
    public BaseResponse<?> findById(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data booking", bookingService.findByIdAndUsername(userDetails.getUsername(), id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Buat booking",
            description = "Membuat booking baru"
    )
    public BaseResponse<?> create(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody CreateBookingRequestRecord request) {
        return BaseResponse.ok("Berhasil membuat booking baru", bookingService.create(userDetails.getUsername(), request));
    }

}

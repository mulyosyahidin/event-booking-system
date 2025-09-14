package com.sinaukoding.martinms.event_booking_system.controller.admin;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.CreateEventRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.UpdateEventRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("AdminEventController")
@RequestMapping("admin/events")
@RequiredArgsConstructor
@Tag(name = "[Admin] Events")
public class EventController {

    private final IBookingService bookingService;
    private final IEventService eventService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Semua event",
            description = "Mendapatkan semua event"
    )
    public BaseResponse<?> findAll(
            @PageableDefault(direction = Sort.Direction.ASC, sort = "modifiedDate")
            @Parameter(hidden = true) Pageable pageable
    ) {
        return BaseResponse.ok("Berhasil mendapatkan data event", eventService.findAll(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buat event",
            description = "Membuat event baru"
    )
    public BaseResponse<SimpleMap> create(@RequestBody CreateEventRequestRecord request) {
        return BaseResponse.ok("Berhasil membuat event baru", eventService.create(request));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Detail event",
            description = "Mendapatkan detail event berdasarkan id"
    )
    public BaseResponse<SimpleMap> findById(@PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data event", eventService.findById(id));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update event",
            description = "Mengubah data event berdasarkan id"
    )
    public BaseResponse<?> update(@PathVariable String id, @RequestBody UpdateEventRequestRecord request) {
        return BaseResponse.ok("Berhasil memperbarui data event", eventService.update(id, request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Hapus event",
            description = "Menghapus event berdasarkan id"
    )
    public BaseResponse<?> delete(@PathVariable String id) {
        eventService.destroy(id);

        return BaseResponse.ok("Berhasil menghapus event", null);
    }

    @GetMapping("{id}/bookings")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Booking event",
            description = "Mendapatkan semua data booking event"
    )
    public BaseResponse<?> findEventBookings(@PageableDefault(direction = Sort.Direction.ASC, sort = "modifiedDate")
                                             @Parameter(hidden = true) Pageable pageable,
                                             @PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data booking event", bookingService.findAllByEventId(id, pageable));
    }

}

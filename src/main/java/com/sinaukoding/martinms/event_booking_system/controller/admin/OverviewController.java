package com.sinaukoding.martinms.event_booking_system.controller.admin;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IBookingService;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "[Admin] Overview")
public class OverviewController {

    private final IBookingService bookingService;
    private final IEventService eventService;
    private final IUserService userService;

    @GetMapping("overview")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Admin Oveview",
            description = "Ringkasan data aplikasi"
    )
    public BaseResponse<?> overview() {
        SimpleMap data = new SimpleMap();

        data.add("user", userService.getOverview());
        data.add("event", eventService.getOverview());
        data.add("booking", bookingService.getOverview());

        return BaseResponse.ok("Berhasil mendapatkan ringkasan aplikasi", data);
    }

}

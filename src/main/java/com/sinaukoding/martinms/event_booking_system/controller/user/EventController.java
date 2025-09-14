package com.sinaukoding.martinms.event_booking_system.controller.user;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserEventController")
@RequestMapping("users/events")
@RequiredArgsConstructor
@Tag(name = "[Users] Events")
public class EventController {

    private final IEventService eventService;

    @GetMapping
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

    @GetMapping("{id}")
    @Operation(
            summary = "Detail event",
            description = "Mendapatkan detail event berdasarkan id"
    )
    public BaseResponse<SimpleMap> findById(@PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data event", eventService.findById(id));
    }

}

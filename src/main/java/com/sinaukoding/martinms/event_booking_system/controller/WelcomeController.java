package com.sinaukoding.martinms.event_booking_system.controller;

import com.sinaukoding.martinms.event_booking_system.config.MidtransProperties;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Welcome Controller")
public class WelcomeController {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${app.base_url}")
    private String baseUrl;

    @GetMapping
    @Operation(
            summary = "Selamat Datang",
            description = "Selamat datang di API Service aplikasi Event Booking System"
    )
    public BaseResponse<?> welcome() {
        SimpleMap data = new SimpleMap();

        data.put("activeProfile", activeProfile);
        data.put("baseUrl", baseUrl);

        return BaseResponse.ok("Selamat datang di Event Booking System", data);
    }

}

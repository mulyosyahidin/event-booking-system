package com.sinaukoding.martinms.event_booking_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Martin MS",
                        email = "martinms.za@gmail.com"
                ),
                description = "Event Booking System API Documentation",
                title = "Event Booking System",
                version = "1.0.0",
                license = @License(
                        name = "Sinau Koding",
                        url = "https://sinaukoding.co.id/"
                )
        ),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Staging",
                        url = "https://event-booking.codewith.cyou"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Bearer Auth Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}

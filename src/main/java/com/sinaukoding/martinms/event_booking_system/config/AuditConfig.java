package com.sinaukoding.martinms.event_booking_system.config;

import com.sinaukoding.martinms.event_booking_system.config.auth.UserLoggedInConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("SYSTEM");
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserLoggedInConfig loggedIn) {
                return Optional.ofNullable(loggedIn.getUser().getId());
            } else if (principal instanceof UserDetails userDetails) {
                return Optional.of(userDetails.getUsername());
            } else {
                return Optional.ofNullable(authentication.getName());
            }
        };
    }

}

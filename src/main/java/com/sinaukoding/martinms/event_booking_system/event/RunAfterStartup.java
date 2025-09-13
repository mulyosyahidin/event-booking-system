package com.sinaukoding.martinms.event_booking_system.event;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {

    private final IUserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<User> adminUsers = userService.getAdminUsers();

        if (adminUsers.isEmpty()) {
            RegisterRequestRecord requestRecord = new RegisterRequestRecord(
                    "Admin",
                    "admin@event.app",
                    "admin",
                    "password",
                    null
            );

            userService.registerNewUser(requestRecord, UserRole.ADMIN);
        }
    }

}

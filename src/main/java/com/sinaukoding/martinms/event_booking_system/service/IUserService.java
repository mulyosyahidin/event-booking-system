package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;

import java.util.List;

public interface IUserService {
    List<User> getAdminUsers();

    User registerNewUser(RegisterRequestRecord requestRecord, UserRole userRole);

    SimpleMap findByUsername(String username);
}

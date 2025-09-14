package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.user.UpdateUserRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    List<User> getAdminUsers();

    User registerNewUser(RegisterRequestRecord requestRecord, UserRole userRole);

    SimpleMap findByUsername(String username);

    Page<SimpleMap> findAll(Pageable pageable);

    SimpleMap findById(String id);

    SimpleMap update(String id, UpdateUserRequestRecord request);

    void destroy(String id);

    SimpleMap getOverview();
}

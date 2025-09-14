package com.sinaukoding.martinms.event_booking_system.service;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.LoginRequestRecord;

public interface IAuthService {
    SimpleMap loginWithUsernameAndPassword(LoginRequestRecord requestRecord);

    void logout(String username);
}

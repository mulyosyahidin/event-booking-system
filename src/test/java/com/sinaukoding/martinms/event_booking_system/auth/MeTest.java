package com.sinaukoding.martinms.event_booking_system.auth;

import com.sinaukoding.martinms.event_booking_system.controller.AuthController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IAuthService;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeTest {

    @Mock
    private IUserService userService;

    @Mock
    private IAuthService authService;

    @InjectMocks
    private AuthController authController;

    private UserDetails userDetails;
    private SimpleMap expectedUserData;
    private static final String USERNAME = "marteen";

    @BeforeEach
    void setUp() {
        userDetails = new org.springframework.security.core.userdetails.User(
                USERNAME,
                "password",
                Collections.emptyList()
        );

        expectedUserData = new SimpleMap();
        expectedUserData.put("username", USERNAME);
    }

    @Test
    void me_Success() {
        when(userService.findByUsername(USERNAME)).thenReturn(expectedUserData);

        BaseResponse<SimpleMap> response = authController.me(userDetails);

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data user", response.getMessage());
        assertEquals(expectedUserData, response.getData());
        verify(userService).findByUsername(USERNAME);
    }

    @Test
    void me_UserNotFound() {
        when(userService.findByUsername(USERNAME)).thenReturn(null);

        BaseResponse<SimpleMap> response = authController.me(userDetails);

        assertNotNull(response);
        assertNull(response.getData());
        assertEquals("Berhasil mendapatkan data user", response.getMessage());
        verify(userService).findByUsername(USERNAME);
    }

}


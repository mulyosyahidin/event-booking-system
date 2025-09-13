package com.sinaukoding.martinms.event_booking_system.auth;

import com.sinaukoding.martinms.event_booking_system.controller.AuthController;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IAuthService;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterTest {

    @Mock
    private IUserService userService;

    @Mock
    private IAuthService authService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthController authController;

    private RegisterRequestRecord registerRequest;
    private User user;
    private SimpleMap userData;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestRecord(
                "marteen", "marteen@example.com", "marteen", "password", null
        );

        user = User.builder()
                .username("marteen")
                .email("marteen@example.com")
                .status(Status.AKTIF)
                .build();

        userData = new SimpleMap();
        userData.put("id", 1L);
        userData.put("username", "marteen");
        userData.put("email", "marteen@example.com");
    }

    @Test
    void register_Success() {
        when(userService.registerNewUser(registerRequest, UserRole.USER))
                .thenReturn(user);
        when(userMapper.entityToSimpleMap(user)).thenReturn(userData);

        BaseResponse<SimpleMap> response = authController.register(registerRequest);

        assertNotNull(response);
        assertEquals("Berhasil mendaftarkan user baru", response.getMessage());
        assertEquals(userData, response.getData().get("user"));
        verify(userService).registerNewUser(registerRequest, UserRole.USER);
        verify(userMapper).entityToSimpleMap(user);
    }

    @Test
    void register_FailedValidation() {
        when(userService.registerNewUser(registerRequest, UserRole.USER))
                .thenThrow(new RuntimeException("Validation failed"));

        assertThrows(RuntimeException.class,
                () -> authController.register(registerRequest));

        verify(userService).registerNewUser(registerRequest, UserRole.USER);
        verify(userMapper, never()).entityToSimpleMap(any());
    }

}

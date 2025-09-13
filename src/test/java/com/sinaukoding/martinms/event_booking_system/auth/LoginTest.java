package com.sinaukoding.martinms.event_booking_system.auth;

import com.sinaukoding.martinms.event_booking_system.config.exception.UnauthorizedException;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.LoginRequestRecord;
import com.sinaukoding.martinms.event_booking_system.repository.UserRepository;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import com.sinaukoding.martinms.event_booking_system.service.impl.AuthServiceImpl;
import com.sinaukoding.martinms.event_booking_system.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginTest {
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IValidatorService validatorService;

    @InjectMocks
    private AuthServiceImpl authService;
    private LoginRequestRecord loginRequest;
    private User user;
    private static final String USERNAME = "marteen";
    private static final String PASSWORD = "marteen";
    private static final String TOKEN = "ini adalah sebuah token jwt yaa";

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestRecord(USERNAME, PASSWORD);
        user = User.builder().username(USERNAME).password("hashedPassword").status(Status.AKTIF).build();
    }

    @Test
    void loginWithUsernameAndPassword_Success() {
        when(userRepository.findByUsernameAndStatus(USERNAME, Status.AKTIF)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(USERNAME)).thenReturn(TOKEN);
        when(userMapper.entityToSimpleMap(any())).thenReturn(new SimpleMap());
        SimpleMap result = authService.loginWithUsernameAndPassword(loginRequest);
        assertNotNull(result);
        assertEquals(TOKEN, result.get("token"));
        verify(userRepository).save(any(User.class));
        verify(validatorService).validator(loginRequest);
    }

    @Test
    void loginWithUsernameAndPassword_UserNotFound() {
        when(userRepository.findByUsernameAndStatus(USERNAME, Status.AKTIF)).thenReturn(Optional.empty());
        assertThrows(UnauthorizedException.class, () -> authService.loginWithUsernameAndPassword(loginRequest));
        verify(validatorService).validator(loginRequest);
    }

    @Test
    void loginWithUsernameAndPassword_InvalidPassword() {
        when(userRepository.findByUsernameAndStatus(USERNAME, Status.AKTIF)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(false);
        assertThrows(UnauthorizedException.class, () -> authService.loginWithUsernameAndPassword(loginRequest));
        verify(validatorService).validator(loginRequest);
        verify(userRepository, never()).save(any());
    }

    @Test
    void loginWithUsernameAndPassword_ValidatesRequest() {
        doThrow(new RuntimeException("Validation failed")).when(validatorService).validator(loginRequest);
        assertThrows(RuntimeException.class, () -> authService.loginWithUsernameAndPassword(loginRequest));
        verify(userRepository, never()).findByUsernameAndStatus(any(), any());
    }

}

package com.sinaukoding.martinms.event_booking_system.auth;

import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutTest {

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

    private User user;
    private static final String USERNAME = "marteen";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username(USERNAME)
                .password("hashedPassword")
                .status(Status.AKTIF)
                .token("someToken")
                .expiredTokenAt(LocalDateTime.now().plusHours(3))
                .build();
    }

    @Test
    void logout_Success() {
        when(userRepository.findByUsernameAndStatus(USERNAME, Status.AKTIF))
                .thenReturn(Optional.of(user));

        authService.logout(USERNAME);

        assertNull(user.getToken());
        assertNull(user.getExpiredTokenAt());
        verify(userRepository).findByUsernameAndStatus(USERNAME, Status.AKTIF);
        verify(userRepository).save(user);
    }

    @Test
    void logout_UserNotFound() {
        when(userRepository.findByUsernameAndStatus(USERNAME, Status.AKTIF))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authService.logout(USERNAME));

        verify(userRepository).findByUsernameAndStatus(USERNAME, Status.AKTIF);
        verify(userRepository, never()).save(any());
    }

}

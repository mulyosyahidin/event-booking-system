package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.config.exception.UnauthorizedException;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.LoginRequestRecord;
import com.sinaukoding.martinms.event_booking_system.repository.UserRepository;
import com.sinaukoding.martinms.event_booking_system.service.IAuthService;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import com.sinaukoding.martinms.event_booking_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final IValidatorService validatorService;

    @Override
    public SimpleMap loginWithUsernameAndPassword(LoginRequestRecord requestRecord) {
        validatorService.validator(requestRecord);

        var user = userRepository.findByUsernameAndStatus(requestRecord.username().toLowerCase(), Status.AKTIF)
                .orElseThrow(() -> new UnauthorizedException("Username atau password salah"));

        if (!passwordEncoder.matches(requestRecord.password(), user.getPassword())) {
            throw new UnauthorizedException("Username atau password salah");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        user.setToken(token);
        user.setExpiredTokenAt(LocalDateTime.now().plusHours(3));
        userRepository.save(user);

        SimpleMap result = new SimpleMap();
        result.put("token", token);
        result.put("user", userMapper.entityToSimpleMap(user));

        return result;
    }

    @Override
    public void logout(String username) {
        User user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException("User " + username + " tidak ditemukan"));

        user.setToken(null);
        user.setExpiredTokenAt(null);

        userRepository.save(user);
    }

}

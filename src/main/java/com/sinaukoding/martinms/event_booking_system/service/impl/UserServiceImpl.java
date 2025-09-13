package com.sinaukoding.martinms.event_booking_system.service.impl;

import com.sinaukoding.martinms.event_booking_system.config.exception.ConflictResourceException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;
import com.sinaukoding.martinms.event_booking_system.repository.UserRepository;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IValidatorService validatorService;

    @Override
    public List<User> getAdminUsers() {
        return userRepository.findAllByRoleAndStatus(UserRole.ADMIN, Status.AKTIF);
    }

    @Override
    public User registerNewUser(RegisterRequestRecord requestRecord, UserRole userRole) {
        validatorService.validator(requestRecord);

        if (userRepository.existsByEmail(requestRecord.email())) {
            throw new ConflictResourceException("Email sudah terdaftar");
        }

        if (userRepository.existsByUsername(requestRecord.username())) {
            throw new ConflictResourceException("Username sudah terdaftar");
        }

        User user = new User();

        user.setUsername(requestRecord.username());
        user.setNama(requestRecord.nama());
        user.setEmail(requestRecord.email());
        user.setPhoneNumber(requestRecord.phoneNumber());
        user.setPassword(passwordEncoder.encode(requestRecord.password()));
        user.setRole(userRole);
        user.setStatus(Status.AKTIF);

        userRepository.save(user);
        return user;
    }

    @Override
    public SimpleMap findByUsername(String username) {
        User user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new ResourceNotFoundException(username));

        return userMapper.entityToSimpleMap(user);
    }

}

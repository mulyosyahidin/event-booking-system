package com.sinaukoding.martinms.event_booking_system.service.app.impl;

import com.sinaukoding.martinms.event_booking_system.config.auth.UserLoggedInConfig;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggedInServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameAndStatus(username, Status.AKTIF)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserLoggedInConfig(user);
    }

}

package com.sinaukoding.martinms.event_booking_system.repository;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.enums.Status;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findAllByRoleAndStatus(UserRole userRole, Status status);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndStatus(String username, Status status);

    Optional<User> findByIdAndStatus(String id, Status status);
}

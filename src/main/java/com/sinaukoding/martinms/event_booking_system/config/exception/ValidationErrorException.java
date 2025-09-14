package com.sinaukoding.martinms.event_booking_system.config.exception;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationErrorException(Set<ConstraintViolation<Object>> constraintViolations) {
        super(constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Terjadi kesalahan validasi"));
    }

}

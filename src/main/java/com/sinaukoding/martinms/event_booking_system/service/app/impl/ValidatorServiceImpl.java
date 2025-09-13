package com.sinaukoding.martinms.event_booking_system.service.app.impl;

import com.sinaukoding.martinms.event_booking_system.config.exception.ValidationErrorException;
import com.sinaukoding.martinms.event_booking_system.service.app.IValidatorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements IValidatorService {

    private final Validator validator;

    @Override
    public void validator(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            throw new ValidationErrorException(constraintViolations);
        }
    }

}

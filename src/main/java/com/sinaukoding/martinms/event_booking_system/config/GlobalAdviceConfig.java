package com.sinaukoding.martinms.event_booking_system.config;

import com.sinaukoding.martinms.event_booking_system.config.exception.ConflictResourceException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.config.exception.UnauthorizedException;
import com.sinaukoding.martinms.event_booking_system.config.exception.ValidationErrorException;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceConfig {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleExceptionBadRequest(Exception e) {
        return BaseResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler({ValidationErrorException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<?> handleValidationErrorException(ValidationErrorException e) {
        return BaseResponse.builder()
                .status(422)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({ConflictResourceException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<?> handleConflictResourceException(ConflictResourceException e) {
        return BaseResponse.builder()
                .status(400)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<?> handleResourceNotFoundException(UnauthorizedException e) {
        return BaseResponse.unauthorizedAccess(e.getMessage());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return BaseResponse.notFound(e.getMessage());
    }

    @ExceptionHandler({
            AuthorizationDeniedException.class,
            AccessDeniedException.class
    })
    public ResponseEntity<BaseResponse<?>> handleAccessDenied(Exception ex) {
        if (ex.getCause() instanceof InsufficientAuthenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.unauthorizedAccess(ex.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.forbiddenAccess(ex.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleException(Exception e) {
        return BaseResponse.error("Terjadi kesalahan tidak terduga", e.getMessage());
    }

}

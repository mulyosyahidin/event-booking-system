package com.sinaukoding.martinms.event_booking_system.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        BaseResponse<?> baseResponse = BaseResponse.unauthorizedAccess("Unauthorized access");

        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}

package com.sinaukoding.martinms.event_booking_system.controller;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.mapper.UserMapper;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.UserRole;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.LoginRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.request.auth.RegisterRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IAuthService;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication API")
@RequiredArgsConstructor
public class AuthController {

    private final UserMapper userMapper;
    private final IUserService userService;
    private final IAuthService authService;

    @PostMapping("login")
    @Operation(
            summary = "Login",
            description = "Login dengan username dan password"
    )
    public BaseResponse<?> login(@RequestBody LoginRequestRecord requestRecord) {
        return BaseResponse.ok("Berhasil login dengan username dan password", authService.loginWithUsernameAndPassword(requestRecord));
    }

    @GetMapping("me")
    @Operation(
            summary = "Me",
            description = "Mendapatkan data user yang sedang login"
    )
    public BaseResponse<SimpleMap> me(@AuthenticationPrincipal UserDetails userDetails) {
        return BaseResponse.ok("Berhasil mendapatkan data user", userService.findByUsername(userDetails.getUsername()));
    }

    @DeleteMapping("logout")
    @Operation(
            summary = "Logout",
            description = "Logout / invalidate token"
    )
    public BaseResponse<?> logout(@AuthenticationPrincipal UserDetails userDetails) {
        authService.logout(userDetails.getUsername());

        return BaseResponse.ok("Berhasil logout", null);
    }

    @PostMapping("register")
    @Operation(
            summary = "Register",
            description = "Mendaftarkan user baru"
    )
    public BaseResponse<SimpleMap> register(@RequestBody RegisterRequestRecord requestRecord) {
        User registeredUser = userService.registerNewUser(requestRecord, UserRole.USER);

        SimpleMap result = new SimpleMap();
        result.put("user", userMapper.entityToSimpleMap(registeredUser));

        return BaseResponse.ok("Berhasil mendaftarkan user baru", result);
    }

}

package com.sinaukoding.martinms.event_booking_system.controller.admin;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.user.UpdateUserRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/users")
@RequiredArgsConstructor
@Tag(name = "[Admin] Users")
public class UserController {

    private final IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Semua users",
            description = "Mendapatkan semua users"
    )
    public BaseResponse<?> findAll(
            @PageableDefault(direction = Sort.Direction.ASC, sort = "modifiedDate")
            @Parameter(hidden = true) Pageable pageable
    ) {
        return BaseResponse.ok("Berhasil mendapatkan data users", userService.findAll(pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Detail user",
            description = "Mendapatkan detail user berdasarkan id"
    )
    public BaseResponse<SimpleMap> findById(@PathVariable String id) {
        return BaseResponse.ok("Berhasil mendapatkan data user", userService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Update user",
            description = "Mengubah data user berdasarkan id"
    )
    public BaseResponse<?> update(@PathVariable String id, @RequestBody UpdateUserRequestRecord request) {
        return BaseResponse.ok("Berhasil memperbarui data user", userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Hapus user",
            description = "Menghapus user berdasarkan id"
    )
    public BaseResponse<?> delete(@PathVariable String id) {
        userService.destroy(id);

        return BaseResponse.ok("Berhasil menghapus user", null);
    }

}

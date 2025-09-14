package com.sinaukoding.martinms.event_booking_system.admin.user;

import com.sinaukoding.martinms.event_booking_system.controller.admin.UserController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.user.UpdateUserRequestRecord;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private SimpleMap userData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userData = new SimpleMap();
        userData.put("id", "1");
        userData.put("username", "martin");
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SimpleMap> page = new PageImpl<>(List.of(userData));

        when(userService.findAll(pageable)).thenReturn(page);

        BaseResponse<?> response = userController.findAll(pageable);

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data users", response.getMessage());
        assertNotNull(response.getData());

        verify(userService).findAll(pageable);
    }

    @Test
    void findById_Success() {
        when(userService.findById("1")).thenReturn(userData);

        BaseResponse<SimpleMap> response = userController.findById("1");

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data user", response.getMessage());
        assertEquals("martin", response.getData().get("username"));

        verify(userService).findById("1");
    }

    @Test
    void update_Success() {
        UpdateUserRequestRecord request = new UpdateUserRequestRecord(
                "Martin",
                "martin@example.com",
                "martin",
                "secret",
                "08123456789"
        );

        when(userService.update(eq("1"), any(UpdateUserRequestRecord.class))).thenReturn(userData);

        BaseResponse<?> response = userController.update("1", request);

        assertNotNull(response);
        assertEquals("Berhasil memperbarui data user", response.getMessage());
        assertNotNull(response.getData());

        verify(userService).update(eq("1"), any(UpdateUserRequestRecord.class));
    }

    @Test
    void delete_Success() {
        doNothing().when(userService).destroy("1");

        BaseResponse<?> response = userController.delete("1");

        assertNotNull(response);
        assertEquals("Berhasil menghapus user", response.getMessage());
        assertNull(response.getData());

        verify(userService).destroy("1");
    }
}
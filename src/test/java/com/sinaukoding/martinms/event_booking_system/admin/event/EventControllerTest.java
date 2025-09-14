package com.sinaukoding.martinms.event_booking_system.admin.event;

import com.sinaukoding.martinms.event_booking_system.config.GlobalAdviceConfig;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.controller.admin.EventController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.request.admin.event.EventRequest;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.IEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private IEventService eventService;

    private MockMvc mockMvc;
    private SimpleMap event;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new GlobalAdviceConfig())
                .build();

        event = new SimpleMap();
        event.put("id", "1");
        event.put("nama", "Seminar Java Spring Boot");
    }

    @Test
    void findAll_Success() {
        Page<SimpleMap> page = new PageImpl<>(List.of(event));
        Pageable pageable = PageRequest.of(0, 10);

        when(eventService.findAll(pageable)).thenReturn(page);

        BaseResponse<?> response = eventController.findAll(pageable);

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data event", response.getMessage());
        assertNotNull(response.getData());

        verify(eventService).findAll(pageable);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void create_Success() {
        EventRequest request = new EventRequest(
                "Seminar Green Tech Future",
                "Teknologi hijau",
                "event/2025/09/14/img.png",
                "SEMINAR",
                "Jakarta",
                "2025-09-15 08:00",
                "2025-09-15 16:00",
                100,
                150000.0
        );

        when(eventService.create(any(EventRequest.class))).thenReturn(event);

        BaseResponse<?> response = eventController.create(request);

        assertNotNull(response);
        assertEquals("Berhasil membuat event baru", response.getMessage());
        assertNotNull(response.getData());

        verify(eventService).create(any(EventRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void findById_Success() {
        when(eventService.findById("1")).thenReturn(event);

        BaseResponse<?> response = eventController.findById("1");

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data event", response.getMessage());
        assertNotNull(response.getData());

        verify(eventService).findById("1");
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void update_Success() {
        EventRequest request = new EventRequest(
                "Seminar Updated",
                "Update konten",
                "event/2025/09/14/img2.png",
                "SEMINAR",
                "Bandung",
                "2025-09-20 08:00",
                "2025-09-20 16:00",
                80,
                120000.0
        );

        when(eventService.update(eq("1"), any(EventRequest.class))).thenReturn(event);

        BaseResponse<?> response = eventController.update("1", request);

        assertNotNull(response);
        assertEquals("Berhasil memperbarui data event", response.getMessage());
        assertNotNull(response.getData());

        verify(eventService).update(eq("1"), any(EventRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void delete_Success() {
        doNothing().when(eventService).destroy("1");

        BaseResponse<?> response = eventController.delete("1");
        
        assertNotNull(response);
        assertEquals("Berhasil menghapus event", response.getMessage());

        verify(eventService).destroy("1");
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void findById_NotFound() {
        when(eventService.findById("404"))
                .thenThrow(new ResourceNotFoundException("Event tidak ditemukan"));

        assertThrows(ResourceNotFoundException.class, () -> eventController.findById("404"));

        verify(eventService).findById("404");
    }

}

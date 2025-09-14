package com.sinaukoding.martinms.event_booking_system.user.event;

import com.sinaukoding.martinms.event_booking_system.config.GlobalAdviceConfig;
import com.sinaukoding.martinms.event_booking_system.config.exception.ResourceNotFoundException;
import com.sinaukoding.martinms.event_booking_system.controller.admin.EventController;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void findById_Success() {
        when(eventService.findById("1")).thenReturn(event);

        BaseResponse<?> response = eventController.findById("1");

        assertNotNull(response);
        assertEquals("Berhasil mendapatkan data event", response.getMessage());
        assertNotNull(response.getData());

        verify(eventService).findById("1");
    }

    @Test
    void findById_NotFound() {
        when(eventService.findById("404"))
                .thenThrow(new ResourceNotFoundException("Event tidak ditemukan"));

        assertThrows(ResourceNotFoundException.class, () -> eventController.findById("404"));

        verify(eventService).findById("404");
    }

}

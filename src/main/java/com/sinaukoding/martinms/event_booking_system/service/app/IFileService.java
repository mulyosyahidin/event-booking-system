package com.sinaukoding.martinms.event_booking_system.service.app;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.TipeUpload;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    SimpleMap upload(MultipartFile files, TipeUpload tipeUpload);

    Resource loadFileAsResource(String pathFile);

}


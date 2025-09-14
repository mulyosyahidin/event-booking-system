package com.sinaukoding.martinms.event_booking_system.controller;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.TipeUpload;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.app.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
@Tag(name = "File Service API")
public class FileController {

    private final IFileService fileService;

    @PostMapping(path = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Upload file",
            description = "Upload file dengan tipe upload"
    )
    public BaseResponse<SimpleMap> uploadFile(@RequestPart MultipartFile file,
                                              @RequestParam TipeUpload tipeUpload) {
        return BaseResponse.ok("Berhasil mengupload file", fileService.upload(file, tipeUpload));
    }

    @GetMapping("view")
    @Operation(
            summary = "Lihat file",
            description = "Lihat file berdasarkan path"
    )
    public void viewFile(@RequestParam String pathFile, HttpServletResponse response) throws IOException {
        Resource resource = fileService.loadFileAsResource(pathFile);
        IOUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

}

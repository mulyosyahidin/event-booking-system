package com.sinaukoding.martinms.event_booking_system.service.app.impl;

import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.model.enums.TipeUpload;
import com.sinaukoding.martinms.event_booking_system.model.response.BaseResponse;
import com.sinaukoding.martinms.event_booking_system.service.app.IFileService;
import com.sinaukoding.martinms.event_booking_system.util.Checks;
import com.sinaukoding.martinms.event_booking_system.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    @Value("${app.upload-directory}")
    private String uploadDirectory;

    @Override
    public SimpleMap upload(MultipartFile files, TipeUpload tipeUpload) {
        Path fileStorageLocation = Paths.get(uploadDirectory).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);

            String uploadedFilePath = storeFile(files, tipeUpload);

            SimpleMap response = new SimpleMap();
            response.put("uploadedFilePath", uploadedFilePath);

            return response;
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String pathFile) {
        try {
            Path fileStorageLocation = Paths.get(uploadDirectory).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(pathFile).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File: " + pathFile + " tidak ditemukan");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File: " + pathFile + " tidak ditemukan", ex);
        }
    }

    private String storeFile(MultipartFile file, TipeUpload tipeUpload) {
        String unique = String.valueOf(System.currentTimeMillis());
        String originalFilename = file.getOriginalFilename();
        Checks.isTrue(StringUtils.isNotBlank(originalFilename), "Filename tidak boleh kosong");
        String fileName = unique.concat("_").concat(originalFilename);
        try {
            if (originalFilename.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFilename);
            }

            LocalDate currentDate = LocalDate.now();
            String tahun = String.valueOf(currentDate.getYear());
            String bulan = String.format("%02d", currentDate.getMonthValue());
            String tanggal = String.format("%02d", currentDate.getDayOfMonth());
            Path tahunDir = Paths.get(uploadDirectory).toAbsolutePath().normalize().resolve(tipeUpload.name().toLowerCase()).resolve(tahun);
            Path bulanDir = tahunDir.resolve(bulan);
            Path tanggalDir = bulanDir.resolve(tanggal);
            Path dateDirectory = Paths.get(uploadDirectory).toAbsolutePath().normalize().resolve(tanggalDir);

            if (!Files.exists(dateDirectory)) {
                Files.createDirectories(dateDirectory);
            }

            Path targetLocation = dateDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return tipeUpload.name().toLowerCase().concat("/").concat(DateUtil.formatLocalDateToString(currentDate)).concat("/").concat(fileName);
        } catch (IOException ex) {
            throw new RuntimeException("Gagal upload file : " + fileName + ". Silahkan dicoba lagi", ex);
        }
    }

}

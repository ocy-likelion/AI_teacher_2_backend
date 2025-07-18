package com.ll.ilta.domain.image.controller;

import com.ll.ilta.domain.image.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageV1Controller {
    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) { // TODO: ResponeDto 구현 필요
        imageUploadService.uploadImage(file);
        return ResponseEntity.ok().build();
    }
}

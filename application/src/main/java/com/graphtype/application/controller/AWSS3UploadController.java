package com.graphtype.application.controller;

import com.graphtype.application.model.AWSS3File;
import com.graphtype.application.service.AWSS3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AWSS3UploadController {
    private final AWSS3UploadService fileUploadService;

    @PostMapping
    public ResponseEntity<AWSS3File> post(
            @RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(fileUploadService.save(multipartFile));
    }
}
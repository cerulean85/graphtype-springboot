//package com.graphtype.controller;
//
//import com.graphtype.component.AwsS3Uploader;
//import com.graphtype.model.File;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@RestController
//public class FileController {
//    private final AwsS3Uploader awsS3Uploader;
//
//    @PostMapping("/upload")
//    public Mono<ResponseEntity<File>> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        String fileName = awsS3Uploader.upload(multipartFile, "images");
//        File file = new File();
//        file.url = fileName;
//        Mono<File> res = Mono.just(file);
//        return res.map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//}
//

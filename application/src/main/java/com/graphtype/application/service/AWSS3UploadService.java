package com.graphtype.application.service;

import com.graphtype.application.component.AWSS3ResourceStorage;
import com.graphtype.application.model.AWSS3File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class AWSS3UploadService {

    private final AWSS3ResourceStorage awsS3ResourceStorage;

    public AWSS3File save(MultipartFile multipartFile) {
        AWSS3File fileDetail = AWSS3File.multipartOf(multipartFile);
        awsS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return fileDetail;
    }
}

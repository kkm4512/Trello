package com.example.trello.domain.attachment.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.trello.common.exception.FileException;
import com.example.trello.common.response.ApiResponseFileEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public void put(Path filePath, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,filePath.toString(),file.getInputStream(),metadata);
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
        }
    }

    public void delete(String filePath){
        amazonS3Client.deleteObject(bucket,filePath);
    }
}

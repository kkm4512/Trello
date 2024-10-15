package com.example.trello.domain.attachment.service;

import com.example.trello.common.exception.FileException;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.trello.common.response.ApiResponseFileEnum.DIRECTORY_CREATE_ERROR;

@Service
public class AttachmentDirectoryService {
    public void mkdir(Path directory){
        try {
            Files.createDirectories(directory);
        } catch (Exception e){
            throw new FileException(DIRECTORY_CREATE_ERROR);
        }
    }
}

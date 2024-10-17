package com.example.trello.domain.attachment.service.util;

import com.example.trello.common.exception.FileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.example.trello.common.path.GlobalPath.*;
import static com.example.trello.common.response.ApiResponseFileEnum.FILE_COPY_ERROR;

@Service
public class AttachmentFileService {
    public String mkFilename(String originFilename){
        return UUID.randomUUID() +
                DELIMITER +
                originFilename;
    }

    public void mkFile(Path path, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream,path);
        } catch (Exception e) {
            throw new FileException(FILE_COPY_ERROR);
        }
    }

    public Path mkFilepath(String card_id, String filename ) {
        return Paths.get(
                FILES,
                card_id,
                UUID.randomUUID().toString(),
                filename
        );
    }
}

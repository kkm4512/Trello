package com.example.trello.domain.attachment.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AttachmentUtilService {
    public String getLastFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }
}

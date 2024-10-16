package com.example.trello.domain.attachment.service;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AttachmentPathService {
    public Path mkPath(Path path, String filename){
        return Paths.get(path.toString(),filename);
    }

    public Path mkFilesCardsPath(String card_id){
        return Paths.get("files")
                .resolve("cards")
                .resolve(card_id);
    }


}

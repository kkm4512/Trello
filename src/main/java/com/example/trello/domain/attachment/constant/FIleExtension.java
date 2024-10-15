package com.example.trello.domain.attachment.constant;

import lombok.Getter;

@Getter
public enum FIleExtension {
    JPG("jpg"),
    PNG("png"),
    PDF("pdf"),
    CSV("csv");

    private final String extension;

    FIleExtension(String extension) {
        this.extension = extension;
    }
}

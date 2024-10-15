package com.example.trello.domain.attachment;

import com.example.trello.domain.attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/{path}")
    public void uploads(@PathVariable String path, @RequestPart(value = "file") List<MultipartFile> files) {
        attachmentService.uploads(Path.of(path),files);
    }

    @GetMapping("/{path}")
    public List<String> getFiles(@PathVariable String path) {
        return attachmentService.getFiles(Path.of(path));
    }

    @DeleteMapping("/{path}")
    public void deletes(@PathVariable String path) {
        attachmentService.deletes(Path.of(path));
    }
}

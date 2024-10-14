package com.example.trello.domain.attachment.service;

import com.example.trello.common.exception.FileException;
import com.example.trello.common.response.ApiResponseFileEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.trello.common.path.GlobalPath.DELIMITER;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Override
    public List<String> uploads(Path path, List<MultipartFile> files) {
        try {
            File dir = new File(path.toString());
            List<String> paths = new ArrayList<>();
            dir.mkdirs();
            for ( MultipartFile file : files ) {
                // 파일이 존재할때만
                if (!(file == null || file.isEmpty())) {
                    String filename = UUID.randomUUID() + DELIMITER + file.getOriginalFilename();
                    Path filePath = Paths.get(path.toString(),filename);
                    InputStream inputStream = file.getInputStream();
                    Files.copy(inputStream, filePath);
                    paths.add(filePath.toString());
                }
            }
            return paths;
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_IO_ERROR);
        }
    }

    @Override
    public List<String> getFiles(Path path) {
        try {
            return Files.list(path)
                    .map(String::valueOf)
                    .toList();
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
        }
    }

    @Override
    public void deletes(Path directory) {
        // 파일일 경우 간단히 삭제
        try {
            Files.delete(directory);
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_DELETE_ERROR);
        }
    }

    @Override
    public void delete(Path path) {
        try {
            // 1. 경로가 디렉터리인지 파일인지 확인 후 삭제
            if (Files.isDirectory(path)) {
                // 디렉터리일 경우 재귀적으로 내부 내용 삭제
                deleteDirectoryRecursively(path);
            } else {
                // 파일일 경우 간단히 삭제
                Files.deleteIfExists(path);
            }
        } catch (Exception e) {
            throw new FileException(ApiResponseFileEnum.FILE_DELETE_ERROR);
        }
    }

    // 재귀적으로 디렉터리와 그 안의 파일들을 삭제하는 메서드
    private void deleteDirectoryRecursively(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                try {
                    Files.delete(file);  // 파일 삭제
                    return FileVisitResult.CONTINUE;
                } catch (Exception e) {
                    throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
                }
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                try {
                    Files.delete(dir);  // 디렉터리 삭제
                    return FileVisitResult.CONTINUE;
                } catch (Exception e) {
                    throw new FileException(ApiResponseFileEnum.FILE_NOT_FOUND);
                }
            }
        });

    }
}

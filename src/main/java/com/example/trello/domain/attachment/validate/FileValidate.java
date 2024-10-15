package com.example.trello.domain.attachment.validate;

import com.example.trello.common.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.trello.common.response.ApiResponseFileEnum.FILE_BIG_SIZE;
import static com.example.trello.common.response.ApiResponseFileEnum.FILE_UN_SUPPORT_FORMAT;
import static com.example.trello.domain.attachment.constant.FIleExtension.*;

public class FileValidate {
    /**
     * 지원하는 파일 형식인지 확인
     */
    public static void isFormatFile(List<MultipartFile> files){
        for ( MultipartFile file : files ){
            String extension = getFileExtension(file);
            if (!extension.equals(JPG.getExtension()) ||
                    extension.equals(PNG.getExtension()) ||
                    extension.equals(CSV.getExtension()) ||
                    extension.equals(PDF.getExtension())
            ){
                throw new FileException(FILE_UN_SUPPORT_FORMAT);
            }
        }
    }

    public static void isSizeBigFile(List<MultipartFile> files){
        for ( MultipartFile file : files ){
            long size = file.getSize();
            if (size > 5242880L) {
                throw new FileException(FILE_BIG_SIZE);
            }
        }
    }

    private static String getFileExtension(MultipartFile file){
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1 );
    }
}

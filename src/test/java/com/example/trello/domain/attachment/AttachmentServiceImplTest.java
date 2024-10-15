package com.example.trello.domain.attachment;

import com.example.trello.domain.attachment.service.AttachmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
//@WebMvcTest(controllers = AttachmentServiceImpl.class)
class AttachmentServiceImplTest {
    @Autowired
    AttachmentService attachmentService;

    @Test
    @DisplayName("파일 업로드 테스트")
    void test1() throws IOException {
    }
}
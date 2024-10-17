package com.example.trello.domain.attachment.service;

import com.example.trello.common.response.ApiResponse;
import com.example.trello.domain.attachment.repository.AttachmentRepository;
import com.example.trello.domain.attachment.service.util.AttachmentDirectoryService;
import com.example.trello.domain.attachment.service.util.AttachmentFileService;
import com.example.trello.domain.attachment.service.util.AttachmentPathService;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.dto.AuthUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class AttachmentServiceImplTest {
    @Mock
    AttachmentPathService pathService;

    @Mock
    AttachmentFileService fileService;

    @Mock
    AttachmentDirectoryService directoryService;

    @Mock
    AttachmentRepository attachmentRepository;

    @Mock
    CardRepository cardRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AttachmentServiceImpl attachmentService;

    @Nested
    public class 파일_업로드_테스트 {
        @Test
        @DisplayName("파일_업로드_성공")
        void 파일_업로드_성공() {
            // given - 모키토 변수 정의
            String expectedMessage = "파일 작업 요청에 성공 하였습니다";
            AuthUser authUser = Mockito.mock(AuthUser.class);
            String card_id = "1";
            User user = Mockito.mock(User.class);
            Card card = Mockito.mock(Card.class);
            Path directory = Path.of("directory");
            String originFilename = "originFilename.jpg";
            Path filePath = Path.of("directory", originFilename); // 유효한 filePath 생성
            MultipartFile file = Mockito.mock(MultipartFile.class);
            List<MultipartFile> files = List.of(file);

            // given - 행동 정의
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            given(cardRepository.findById(anyLong())).willReturn(Optional.of(card));
            given(pathService.mkFilesCardsPath(anyString())).willReturn(directory);
            given(fileService.mkFilename(any())).willReturn(originFilename);
            given(pathService.mkPath(any(), any())).willReturn(filePath);
            doNothing().when(fileService).mkFile(any(), any());

            // when
            ApiResponse<List<String>> actual = attachmentService.uploads(authUser, card_id, files);

            // then
            assertEquals(
                    expectedMessage,
                    actual.getMessage()
            );
        }
    }
    @Nested
    public class 파일_다운로드_테스트 {
        @Test
        @DisplayName("파일_다운로드_성공")
        void 파일_다운로드_성공(@TempDir Path tempDir) {
            // given - 변수 정의
            String expectedMessage = "파일 작업 요청에 성공 하였습니다";
            String card_id = "1";
            List<String> filaNames = List.of("test.jpg","test1.jpg","test2.jpg","test3.jpg");

            // given - 행동 정의
            given(pathService.mkFilesCardsPath(anyString())).willReturn(tempDir);

            // when
            ApiResponse<List<String>> actual = attachmentService.downloads(card_id,filaNames);

            // then
            assertEquals(
                    expectedMessage,
                    actual.getMessage()
            );
        }
    }

    @Nested
    public class 파일_삭제_테스트 {
        @Test
        @DisplayName("파일_삭제_성공")
        void 파일_삭제_성공(@TempDir Path tempDir) {
            // given
            String expectedMessage = "파일 작업 요청에 성공 하였습니다";
            String card_id = "1";
            List<String> filaNames = List.of("test.jpg","test1.jpg","test2.jpg","test3.jpg");

            // given - 행동 정의
            given(pathService.mkFilesCardsPath(anyString())).willReturn(tempDir);

            // when
            ApiResponse<List<String>> actual = attachmentService.deletes(card_id,filaNames);

            // then
            assertEquals(
                    expectedMessage,
                    actual.getMessage()
            );
        }
    }
}
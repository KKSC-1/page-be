package KKSC.page.domain.notice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import KKSC.page.domain.notice.service.NoticeFileService;
import KKSC.page.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@WebMvcTest(controllers = NoticeFileController.class)
@Slf4j
public class NoticeFileControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticeFileService noticeFileService;

    @MockBean
    private JwtService jwtService;



    private static final Long NOTICE_BOARD_ID = 1L;
    private static final Long NOTICE_FILE_ID = 1L;
    String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock 서비스 레이어의 동작 설정
        given(noticeFileService.uploadFile(any(), eq(NOTICE_BOARD_ID)))
        .willReturn("File uploaded successfully");

        // 올바른 Resource 타입을 반환하도록 설정
        given(noticeFileService.downloadFile(NOTICE_FILE_ID))
         .willReturn(new ByteArrayResource("file content".getBytes()));

        given(noticeFileService.deleteFile(NOTICE_FILE_ID))
         .willReturn("File deleted successfully");

        jwtToken = jwtService.createAccessToken("Testuser");

    }

    @Test
    @WithMockUser(username= "Testuser")
    void testNoticeFileUpload() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("uploadRequestFile", "filename.txt", "text/plain", "file content".getBytes());

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        request.setAttribute("uploadRequestFile", mockFile);
        MvcResult result = mockMvc.perform(multipart("/noticefile/{noticeboardid}", NOTICE_BOARD_ID,request)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("File uploaded successfully");
    }

    @Test
    @WithMockUser(username= "Testuser")
    void testNoticeFileDownload() throws Exception {
        MvcResult result = mockMvc.perform(get("/noticefile/{noticeFileId}", NOTICE_FILE_ID)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andReturn();

        byte[] content = result.getResponse().getContentAsByteArray();
        assertThat(content).isNotEmpty();
        assertThat(new String(content)).isEqualTo("file content");
    }

    @Test
    @WithMockUser(username= "Testuser")
    void testNoticeFileDelete() throws Exception {
        MvcResult result = mockMvc.perform(delete("/noticefile/{noticeFileId}", NOTICE_FILE_ID)
                .header(HttpHeaders.AUTHORIZATION,jwtToken)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("File deleted successfully");
    }
}
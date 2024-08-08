//package KKSC.page.domain.notice.controller;
//
//import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
//import KKSC.page.domain.notice.dto.NoticeBoardRequest;
//import KKSC.page.domain.notice.service.NoticeBoardService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(NoticeBoardController.class)
//@ComponentScan(basePackages = {"KKSC.page"})
//class NoticeBoardControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    NoticeBoardService noticeBoardService;
//
//    private static final Long TEST_BOARD_ID = 1L;
//    private static final Long TEST_BOARD_CREATOR_ID = 0L;
//    private static final String TEST_USERNAME = "admin";
//
//    @Test
//    @WithMockUser
//    void 게시글_단건_조회() throws Exception {
//        //given
//        NoticeBoardDetailResponse mockResponse = new NoticeBoardDetailResponse("title", "content",
//                "writer", TEST_BOARD_CREATOR_ID, 0L, LocalDateTime.now(), LocalDateTime.now(), null);
//        given(noticeBoardService.getBoardDetail(TEST_BOARD_ID)).willReturn(mockResponse);
//
//        //when & then
//        mockMvc.perform(get("/notice/" + TEST_BOARD_ID))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value(mockResponse.title()))
//                .andExpect(jsonPath("$.data.content").value(mockResponse.content()));
//    }
//
//    @Test
//    @WithMockUser
//    void 게시글_작성_성공() throws Exception {
//        //given
//        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", TEST_BOARD_CREATOR_ID);
//        given(noticeBoardService.create(request, TEST_USERNAME)).willReturn(TEST_BOARD_CREATOR_ID);
//
//        //when & then
//        mockMvc.perform(post("/notice/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request))
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andDo(print())
//                .andExpect(jsonPath("$.data").value(TEST_BOARD_CREATOR_ID))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser
//    void 게시글_수정() throws Exception {
//        //given
//        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", TEST_BOARD_CREATOR_ID);
//
//        given(noticeBoardService.update(TEST_BOARD_ID, updateRequest)).willReturn(new NoticeBoardDetailResponse("title-update", "content-update",
//                TEST_USERNAME, TEST_BOARD_CREATOR_ID, 0L, LocalDateTime.now(), LocalDateTime.now(), null));
//
//        //when & then
//        mockMvc.perform(put("/notice/" + TEST_BOARD_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest))
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("title-update"))
//                .andExpect(jsonPath("$.data.content").value("content-update"));
//    }
//
//    @Test
//    @WithMockUser
//    void 게시글_삭제_성공() throws Exception {
//        // given
//        willDoNothing().given(noticeBoardService).delete(TEST_BOARD_ID);
//
//        // when & then
//        mockMvc.perform(delete("/notice/" + TEST_BOARD_ID)
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("Delete success"));
//    }
//}

package KKSC.page.global.securityconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

//    private String createMockJwtToken(String role) {
//        // 실제 JWT 생성 로직 없이 간단히 모킹된 JWT를 반환합니다.
//        return "mock.jwt.token.with.role." + role;
//    }
//
//    @Test
//    public void testWithMockJwtToken() throws Exception {
//        String mockToken = createMockJwtToken("ROLE_permission_level0");
//
//        mockMvc.perform(get("/profile_edit")
//                        .header("Authorization", "Bearer " + mockToken))
//                .andExpect(status().isOk());
//    }

    /*
    * MockUser 준회원 - 로그인, 회원가입, 프로필 페이지에 접근 가능 확인
    * */
    @Test
    @WithMockUser(roles = "permission_level2")
    public void testPermissionPublicAccess() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    /*
    * MockUser 관리자 -관리자 페이지 접근 가능 확인
    * */
    @Test
    @WithMockUser(roles = "permission_level0")
    public void testPermissionLevel0Access() throws Exception {
        mockMvc.perform(get("/profile_edit"))
                .andExpect(status().isOk());
    }

    /*
    * MockUser 정회원 - 관리자 페이지 접근 불가 + access-denied 페이지로 이동 확인
    * */
    @Test
    @WithMockUser(roles = "permission_level1")
    public void testPermissionLevel1Access() throws Exception {
        mockMvc.perform(get("/profile_edit"))
                .andExpect(status().isForbidden());
    }

    /*
     * MockUser 준회원 - 관리자 페이지 접근 불가 + access-denied 페이지로 이동 확인
     * */
    @Test
    @WithMockUser(roles = "permission_level2")
    public void testPermissionLevel2Access() throws Exception {
        mockMvc.perform(get("/profile_edit"))
                .andExpect(status().isForbidden());
    }
}

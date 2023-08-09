package com.funeat.auth.presentation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.funeat.auth.application.AuthService;
import com.funeat.auth.dto.SignUserDto;
import com.funeat.member.domain.Member;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthApiController.class)
@SuppressWarnings("NonAsciiCharacters")
public class AuthApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Nested
    class loginAuthorizeUser_테스트 {

        @Test
        void 이미_가입된_유저라면_홈_경로로_리다이렉트한다() throws Exception {
            // given
            final var code = "test";
            final var member = new Member("test", "www.test.com", "1");
            final var signUserDto = SignUserDto.of(false, member);

            // when
            when(authService.loginWithKakao(code)).thenReturn(signUserDto);

            // then
            mockMvc.perform(get("/api/login/oauth2/code/kakao")
                            .param("code", code))
                    .andExpect(status().isOk())
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        void 가입되지_않은_유저라면_프로필_경로로_리다이렉트한다() throws Exception {
            // given
            final var code = "test";
            final var member = new Member("test", "www.test.com", "1");
            final var signUserDto = SignUserDto.of(true, member);

            // when
            when(authService.loginWithKakao(code)).thenReturn(signUserDto);

            // then
            mockMvc.perform(get("/api/login/oauth2/code/kakao")
                            .param("code", code))
                    .andExpect(status().isOk())
                    .andExpect(redirectedUrl("/profile"));
        }
    }
}

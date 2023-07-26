package com.funeat.auth.presentation;

import com.funeat.auth.application.AuthService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoLogin(@RequestParam("code") final String code,
                                           final HttpServletRequest request) {
        final Long memberId = authService.loginWithKakao(code);
        request.getSession().setAttribute("member", memberId);
        return ResponseEntity.ok().build();
    }
}

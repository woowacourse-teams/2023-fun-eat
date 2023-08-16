package com.funeat.auth.presentation;

import com.funeat.auth.application.AuthService;
import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.util.AuthenticationPrincipal;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiController implements AuthController {

    private static final String HOME = "/";
    private static final String MY_PAGE = "/members";
    private final AuthService authService;

    public AuthApiController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/auth/kakao")
    public ResponseEntity<Void> kakaoLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authService.getLoginRedirectUri()))
                .build();
    }

    @GetMapping("/api/login/oauth2/code/kakao")
    public ResponseEntity<Void> loginAuthorizeUser(@RequestParam("code") final String code,
                                                   final HttpServletRequest request) {
        final SignUserDto signUserDto = authService.loginWithKakao(code);
        final Long memberId = signUserDto.getMember().getId();
        request.getSession().setAttribute("member", memberId);

        if (signUserDto.isSignUp()) {
            return ResponseEntity.ok()
                    .location(URI.create(MY_PAGE))
                    .build();
        }
        return ResponseEntity.ok()
                .location(URI.create(HOME))
                .build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal final LoginInfo loginInfo,
                                       final HttpServletRequest request) {
        request.getSession().removeAttribute("member");
        authService.logoutWithKakao(loginInfo.getId());

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(HOME))
                .build();
    }
}

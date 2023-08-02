package com.funeat.auth.presentation;

import com.funeat.auth.application.AuthService;
import com.funeat.auth.dto.LoginRequest;
import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.util.AuthenticationPrincipal;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiController implements AuthController {

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

        if (signUserDto.isSignIn()) {
            return ResponseEntity.ok()
                    .location(URI.create("/"))
                    .build();
        }
        return ResponseEntity.ok()
                .location(URI.create("/profile"))
                .build();
    }

    @GetMapping("/api/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal final LoginRequest loginRequest,
                                       final HttpServletRequest request) {
        request.getSession().removeAttribute("member");

        return ResponseEntity.ok().build();
    }
}

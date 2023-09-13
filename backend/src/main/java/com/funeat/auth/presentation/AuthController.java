package com.funeat.auth.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "06.Login", description = "로그인 관련 API 입니다.")
public interface AuthController {

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 한다")
    @ApiResponse(
            responseCode = "302",
            description = "로그인이 성공하여 리다이렉트함"
    )
    @GetMapping
    ResponseEntity<Void> kakaoLogin();

    @Operation(summary = "유저 인증", description = "유저 인증을 한다")
    @ApiResponse(
            responseCode = "302",
            description = "기존 회원이면 홈으로 이동, 신규 회원이면 마이페이지로 이동."
    )
    @GetMapping
    ResponseEntity<Void> loginAuthorizeUser(@RequestParam("code") String code, HttpServletRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃을 한다")
    @ApiResponse(
            responseCode = "302",
            description = "로그아웃 성공."
    )
    @PostMapping
    ResponseEntity<Void> logout(@AuthenticationPrincipal LoginInfo loginInfo, HttpServletRequest request,
                                HttpServletResponse response);
}

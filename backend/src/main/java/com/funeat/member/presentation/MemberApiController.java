package com.funeat.member.presentation;

import com.funeat.auth.dto.LoginRequest;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.member.application.MemberService;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController implements MemberController {

    private final MemberService memberService;

    public MemberApiController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/members")
    public ResponseEntity<MemberProfileResponse> getMemberProfile(
            @AuthenticationPrincipal final LoginRequest loginRequest) {
        final Long memberId = loginRequest.getId();

        final MemberProfileResponse response = memberService.getMemberProfile(memberId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/members")
    public ResponseEntity<Void> putMemberProfile(@AuthenticationPrincipal final LoginRequest loginRequest,
                                                 @RequestBody final MemberRequest request) {
        final Long memberId = loginRequest.getId();

        memberService.modify(memberId, request);

        return ResponseEntity.ok().build();
    }
}

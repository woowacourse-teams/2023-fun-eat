package com.funeat.member.presentation;

import com.funeat.member.application.MemberService;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController implements MemberController {

    private final MemberService memberService;

    public MemberApiController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberProfileResponse> getMemberProfile(@PathVariable final Long memberId) {
        final MemberProfileResponse response = memberService.getMemberProfile(memberId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/members/{memberId}")
    public ResponseEntity<Void> putMemberProfile(@PathVariable final Long memberId,
                                                 @RequestBody final MemberRequest request) {
        memberService.modify(memberId, request);

        return ResponseEntity.ok().build();
    }
}

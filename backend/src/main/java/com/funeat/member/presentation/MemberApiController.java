package com.funeat.member.presentation;

import com.funeat.member.application.MemberService;
import com.funeat.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/api/members/{memberId}")
    public ResponseEntity<Void> putMemberProfile(@PathVariable final Long memberId,
                                                 @RequestBody final MemberRequest request) {
        memberService.modify(memberId, request);

        return ResponseEntity.ok().build();
    }
}

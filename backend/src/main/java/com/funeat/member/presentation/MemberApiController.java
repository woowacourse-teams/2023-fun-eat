package com.funeat.member.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.member.application.MemberService;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.dto.MemberReviewsResponse;
import com.funeat.review.application.ReviewService;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController implements MemberController {

    private final MemberService memberService;
    private final ReviewService reviewService;

    public MemberApiController(final MemberService memberService, final ReviewService reviewService) {
        this.memberService = memberService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<MemberProfileResponse> getMemberProfile(
            @AuthenticationPrincipal final LoginInfo loginInfo) {
        final Long memberId = loginInfo.getId();

        final MemberProfileResponse response = memberService.getMemberProfile(memberId);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> putMemberProfile(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                 @RequestBody @Valid final MemberRequest request) {
        final Long memberId = loginInfo.getId();

        memberService.modify(memberId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<MemberReviewsResponse> getMemberReview(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                 final Pageable pageable) {
        final MemberReviewsResponse response = reviewService.findReviewByMember(loginInfo.getId(), pageable);

        return ResponseEntity.ok().body(response);
    }
}

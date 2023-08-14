package com.funeat.acceptance.member;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.member.MemberSteps.사용자_리뷰_조회_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_수정_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_조회_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.dto.MemberReviewDto;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    class getMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_확인하다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 사용자_정보_조회_요청(loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            사용자_정보_조회를_검증하다(response, member);
        }
    }

    @Nested
    class getMemberProfile_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_사용자_정보를_확인시_예외가_발생한다() {
            // given & when
            final var response = 사용자_정보_조회_요청(null);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            사용자_승인되지_않음을_검증하다(response);
        }
    }

    @Nested
    class putMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_수정하다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest("after", "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
        }
    }

    @Nested
    class putMemberProfile_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_사용자_정보_수정시_예외가_발생한다() {
            // given
            final var request = new MemberRequest("after", "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(null, request);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            사용자_승인되지_않음을_검증하다(response);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_닉네임_미기입시_예외가_발생한다(final String nickname) {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest(nickname, "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "닉네임을 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_이미지_미기입시_예외가_발생한다(final String image) {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest("test", image);

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "프로필 이미지를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }
    }


    @Nested
    class getMemberReviews_성공_테스트 {

        @Test
        void 사용자가_작성한_리뷰를_조회하다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var review1_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product3, 0L);
            final var review2_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product2, 0L);
            final var review2_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member2, product2, 0L);
            final var review3_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
            final var review3_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
            복수_리뷰_저장(review1_1, review2_1, review2_2, review3_1, review3_2);

            final var member1SortedReviews = List.of(review3_1, review2_1, review1_1);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 사용자_리뷰_조회_요청(loginCookie, "createdAt,desc", 0);
            final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);

            // then
            final var expectedReviews = member1SortedReviews.stream()
                    .map(MemberReviewDto::toDto)
                    .collect(Collectors.toList());

            STATUS_CODE를_검증한다(response, 정상_처리);
            사용자_리뷰_목록을_검증한다(response, expectedReviews, page);
        }

        @Test
        void 사용자가_작성한_리뷰가_없을때_리뷰는_빈상태로_조회된다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var review1_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member2, product3, 0L);
            final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
            복수_리뷰_저장(review1_1, review1_2);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 사용자_리뷰_조회_요청(loginCookie, "createdAt,desc", 0);
            final var page = new SortingReviewsPageDto(0L, 0L, true, true, 0L, 10L);

            // then
            final var expectedReviews = Collections.emptyList();

            STATUS_CODE를_검증한다(response, 정상_처리);
            사용자_리뷰_목록을_검증한다(response, expectedReviews, page);
        }
    }

    @Nested
    class getMemberReviews_실패_테스트 {

        @Test
        void 로그인하지_않은_사용자가_작성한_리뷰를_조회할때_예외가_발생한다() {
            // given & when
            final var response = 사용자_리뷰_조회_요청(null, "createdAt,desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    private <T> void 사용자_리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<T> reviews,
                                     final SortingReviewsPageDto page) {
        페이지를_검증한다(response, page);
        사용자_리뷰_목록을_검증한다(response, reviews);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final SortingReviewsPageDto expected) {
        final var actual = response.jsonPath().getObject("page", ProductsInCategoryPageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private <T> void 사용자_리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<T> expectedReviews) {
        final var actual = response.jsonPath().getList("reviews", MemberReviewDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expectedReviews);
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private void 사용자_정보_조회를_검증하다(final ExtractableResponse<Response> response, final Member member) {
        final var expected = MemberProfileResponse.toResponse(member);
        final var expectedNickname = expected.getNickname();
        final var expectedProfileImage = expected.getProfileImage();

        final var actualNickname = response.jsonPath().getString("nickname");
        final var actualProfileImage = response.jsonPath().getString("profileImage");

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualNickname)
                    .isEqualTo(expectedNickname);
            softAssertions.assertThat(actualProfileImage)
                    .isEqualTo(expectedProfileImage);
        });
    }

    private void 사용자_승인되지_않음을_검증하다(final ExtractableResponse<Response> response) {
        final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
        final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

        final var actualCode = response.jsonPath().getString("code");
        final var actualMessage = response.jsonPath().getString("message");

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualCode)
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(actualMessage)
                    .isEqualTo(expectedMessage);
        });
    }
}

package com.funeat.acceptance.member;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.여러개_사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.페이지를_검증한다;
import static com.funeat.acceptance.member.MemberSteps.사용자_꿀조합_조회_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_리뷰_조회_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_수정_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_작성_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.유저닉네임수정요청_생성;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRecipeDto;
import com.funeat.member.dto.MemberReviewDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
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
            단일_멤버_저장(멤버_멤버1_생성());

            // when
            final var response = 사용자_정보_조회_요청(로그인_쿠키_획득(1L));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            사용자_정보_조회를_검증하다(response, 멤버_멤버1_생성());
        }
    }

    @Nested
    class getMemberProfile_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_사용자_정보를_확인시_예외가_발생한다(final String cookie) {
            // given & when
            final var response = 사용자_정보_조회_요청(cookie);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class putMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_수정하다() {
            // given && when
            final var response = 사용자_정보_수정_요청(로그인_쿠키_획득(1L), 사진_명세_요청("1"), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
        }

        @Test
        void 사용자_닉네임을_수정하다() {
            // given && when
            final var response = 사용자_정보_수정_요청(로그인_쿠키_획득(1L), 사진_명세_요청("1"), 유저닉네임수정요청_생성("member1"));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
        }

        @Test
        void 사용자_이미지를_수정하다() {
            // given && when
            final var response = 사용자_정보_수정_요청(로그인_쿠키_획득(1L), 사진_명세_요청("2"), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
        }
    }

    @Nested
    class putMemberProfile_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_사용자_정보_수정시_예외가_발생한다(final String cookie) {
            // given && when
            final var response = 사용자_정보_수정_요청(cookie, 사진_명세_요청("1"), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_닉네임_미기입시_예외가_발생한다(final String nickname) {
            // given && when
            final var response = 사용자_정보_수정_요청(로그인_쿠키_획득(1L), 사진_명세_요청("1"), 유저닉네임수정요청_생성(nickname));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "닉네임을 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class getMemberReviews_성공_테스트 {

        @Test
        void 사용자가_작성한_리뷰를_조회하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매X_생성(2L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(1L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 사용자_리뷰_조회_요청(로그인_쿠키_획득(1L), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            사용자_리뷰_조회_결과를_검증한다(response, 2);
        }

        @Test
        void 사용자가_작성한_리뷰가_없을때_리뷰는_빈상태로_조회된다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(2L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매X_생성(2L, List.of(1L)));

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 사용자_리뷰_조회_요청(로그인_쿠키_획득(1L), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            사용자_리뷰_조회_결과를_검증한다(response, 0);
        }
    }

    @Nested
    class getMemberReviews_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인하지_않은_사용자가_작성한_리뷰를_조회할때_예외가_발생한다(final String cookie) {
            // given & when
            final var response = 사용자_리뷰_조회_요청(cookie, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getMemberRecipes_성공_테스트 {

        @Test
        void 사용자가_작성한_꿀조합을_조회하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(2L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(1L), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            사용자_꿀조합_조회_결과를_검증한다(response, List.of(2L, 1L));
        }

        @Test
        void 사용자가_작성한_꿀조합이_없을때_꿀조합은_빈상태로_조회된다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(2L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(1L), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            사용자_꿀조합_조회_결과를_검증한다(response, Collections.emptyList());
        }

        @Test
        void 사용자가_작성한_꿀조합에_이미지가_없을때_꿀조합은_이미지없이_조회된다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), null, 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(1L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(1L), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            사용자_꿀조합_조회_결과를_검증한다(response, List.of(1L));
            조회한_꿀조합의_이미지가_없는지_확인한다(response);
        }
    }

    @Nested
    class getMemberRecipes_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인하지_않은_사용자가_작성한_꿀조합을_조회할때_예외가_발생한다(final String cookie) {
            // given & when
            final var response = 사용자_꿀조합_조회_요청(cookie, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    private void 사용자_리뷰_조회_결과를_검증한다(final ExtractableResponse<Response> response, final int expectedReviewSize) {
        final var actual = response.jsonPath().getList("reviews", MemberReviewDto.class);

        assertThat(actual.size()).isEqualTo(expectedReviewSize);
    }

    private void 사용자_꿀조합_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath()
                .getList("recipes", MemberRecipeDto.class);

        assertThat(actual).extracting(MemberRecipeDto::getId)
                .containsExactlyElementsOf(recipeIds);
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(soft -> {
            soft.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            soft.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private void 사용자_정보_조회를_검증하다(final ExtractableResponse<Response> response, final Member member) {
        final var expected = MemberProfileResponse.toResponse(member);
        final var expectedNickname = expected.getNickname();
        final var expectedProfileImage = expected.getProfileImage();

        final var actualNickname = response.jsonPath().getString("nickname");
        final var actualProfileImage = response.jsonPath().getString("profileImage");

        assertSoftly(soft -> {
            soft.assertThat(actualNickname)
                    .isEqualTo(expectedNickname);
            soft.assertThat(actualProfileImage)
                    .isEqualTo(expectedProfileImage);
        });
    }

    private void 조회한_꿀조합의_이미지가_없는지_확인한다(final ExtractableResponse<Response> response) {
        final var actual = response.jsonPath()
                .getString("image");

        assertThat(actual).isNull();
    }
}

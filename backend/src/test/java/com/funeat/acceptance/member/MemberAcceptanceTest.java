package com.funeat.acceptance.member;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.여러개_사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.common.CommonSteps.페이지를_검증한다;
import static com.funeat.acceptance.member.MemberSteps.리뷰_삭제_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_꿀조합_조회_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_리뷰_조회_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_수정_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_작성_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.ImageFixture.이미지1;
import static com.funeat.fixture.ImageFixture.이미지2;
import static com.funeat.fixture.ImageFixture.이미지3;
import static com.funeat.fixture.MemberFixture.멤버1;
import static com.funeat.fixture.MemberFixture.멤버2;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.유저닉네임수정요청_생성;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.마지막페이지O;
import static com.funeat.fixture.PageFixture.응답_페이지_생성;
import static com.funeat.fixture.PageFixture.첫페이지O;
import static com.funeat.fixture.PageFixture.총_데이터_개수;
import static com.funeat.fixture.PageFixture.총_페이지;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피;
import static com.funeat.fixture.RecipeFixture.레시피1;
import static com.funeat.fixture.RecipeFixture.레시피2;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰1;
import static com.funeat.fixture.ReviewFixture.리뷰2;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.ScoreFixture.점수_1점;
import static com.funeat.fixture.ScoreFixture.점수_2점;
import static com.funeat.fixture.ScoreFixture.점수_3점;
import static com.funeat.fixture.ScoreFixture.점수_4점;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
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
            // given & when
            final var 응답 = 사용자_정보_조회_요청(로그인_쿠키_획득(멤버1));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            사용자_정보_조회를_검증하다(응답, 멤버_멤버1_생성());
        }
    }

    @Nested
    class getMemberProfile_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_사용자_정보를_확인시_예외가_발생한다(final String cookie) {
            // given & when
            final var 응답 = 사용자_정보_조회_요청(cookie);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class putMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_수정하다() {
            // given & when
            final var 응답 = 사용자_정보_수정_요청(로그인_쿠키_획득(멤버1), 사진_명세_요청(이미지1), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }

        @Test
        void 사용자_닉네임을_수정하다() {
            // given & when
            final var 응답 = 사용자_정보_수정_요청(로그인_쿠키_획득(멤버1), 사진_명세_요청(이미지1), 유저닉네임수정요청_생성("member1"));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }

        @Test
        void 사용자_이미지를_수정하다() {
            // given & when
            final var 응답 = 사용자_정보_수정_요청(로그인_쿠키_획득(멤버1), 사진_명세_요청(이미지2), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }
    }

    @Nested
    class putMemberProfile_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_사용자_정보_수정시_예외가_발생한다(final String cookie) {
            // given & when
            final var 응답 = 사용자_정보_수정_요청(cookie, 사진_명세_요청(이미지1), 유저닉네임수정요청_생성("after"));

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_닉네임_미기입시_예외가_발생한다(final String nickname) {
            // given & when
            final var 응답 = 사용자_정보_수정_요청(로그인_쿠키_획득(멤버1), 사진_명세_요청(이미지1), 유저닉네임수정요청_생성(nickname));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "닉네임을 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class getMemberReviews_성공_테스트 {

        @Test
        void 사용자가_작성한_리뷰를_조회하다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매X_생성(점수_2점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_1점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(2L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 사용자_리뷰_조회_요청(로그인_쿠키_획득(멤버1), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            사용자_리뷰_조회_결과를_검증한다(응답, 2);
        }

        @Test
        void 사용자가_작성한_리뷰가_없을때_리뷰는_빈상태로_조회된다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매X_생성(점수_2점, List.of(태그)));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(0L), 총_페이지(0L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 사용자_리뷰_조회_요청(로그인_쿠키_획득(멤버1), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            사용자_리뷰_조회_결과를_검증한다(응답, 0);
        }
    }

    @Nested
    class getMemberReviews_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인하지_않은_사용자가_작성한_리뷰를_조회할때_예외가_발생한다(final String cookie) {
            // given & when
            final var 응답 = 사용자_리뷰_조회_요청(cookie, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getMemberRecipes_성공_테스트 {

        @Test
        void 사용자가_작성한_꿀조합을_조회하다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버2), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(2L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(멤버1), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            사용자_꿀조합_조회_결과를_검증한다(응답, List.of(레시피2, 레시피1));
        }

        @Test
        void 사용자가_작성한_꿀조합이_없을때_꿀조합은_빈상태로_조회된다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버2), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(0L), 총_페이지(0L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(멤버1), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            사용자_꿀조합_조회_결과를_검증한다(응답, Collections.emptyList());
        }

        @Test
        void 사용자가_작성한_꿀조합에_이미지가_없을때_꿀조합은_이미지없이_조회된다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), null, 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(1L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 사용자_꿀조합_조회_요청(로그인_쿠키_획득(멤버1), 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            사용자_꿀조합_조회_결과를_검증한다(응답, List.of(레시피));
            조회한_꿀조합의_이미지가_없는지_확인한다(응답);
        }
    }

    @Nested
    class getMemberRecipes_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인하지_않은_사용자가_작성한_꿀조합을_조회할때_예외가_발생한다(final String cookie) {
            // given & when
            final var 응답 = 사용자_꿀조합_조회_요청(cookie, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class deleteReview_성공_테스트 {

        @Test
        void 자신이_작성한_리뷰를_삭제할_수_있다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_삭제_요청(로그인_쿠키_획득(멤버1), 리뷰1);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }
    }

    @Nested
    class deleteReview_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인하지_않는_사용자가_리뷰_삭제시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_삭제_요청(cookie, 리뷰1);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(), LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 존재하지_않는_리뷰를_삭제할_수_없다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_삭제_요청(로그인_쿠키_획득(멤버1), 리뷰2);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REVIEW_NOT_FOUND.getCode(), REVIEW_NOT_FOUND.getMessage());
        }

        @Test
        void 자신이_작성하지_않은_리뷰는_삭제할_수_없다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_삭제_요청(로그인_쿠키_획득(멤버2), 리뷰1);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
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

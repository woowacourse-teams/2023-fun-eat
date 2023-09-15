package com.funeat.acceptance.review;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_랭킹_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.정렬된_리뷰_목록_조회_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.평점_내림차순;
import static com.funeat.fixture.PageFixture.평점_오름차순;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.review.dto.RankingReviewDto;
import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import com.funeat.review.dto.SortingReviewDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeReview_성공_테스트 {

        @Test
        void 리뷰를_작성한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"),
                    리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }

        @Test
        void 이미지가_없어도_리뷰를_작성할_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, null,
                    리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }

    @Nested
    class writeReview_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰_작성시_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var response = 리뷰_작성_요청(cookie, 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_NULL일시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, null));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "태그 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"),
                    리뷰추가요청_재구매O_생성(4L, Collections.emptyList()));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "적어도 1개의 태그 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_평점이_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"),
                    리뷰추가요청_재구매O_생성(null, List.of(1L)));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "평점을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_리뷰_작성할때_리뷰내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var request = new ReviewCreateRequest(1L, List.of(1L), content, true);

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "리뷰 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_재구매여부가_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var request = new ReviewCreateRequest(1L, List.of(1L), "content", null);

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "재구매 여부를 입력해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_리뷰내용이_200자_초과시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var maxContent = "test".repeat(50) + "a";
            final var request = new ReviewCreateRequest(1L, List.of(1L), maxContent, true);

            // when
            final var response = 리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "리뷰 내용은 최대 200자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class toggleLikeReview_성공_테스트 {

        @Test
        void 리뷰에_좋아요를_할_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // when
            final var response = 리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 1L, 리뷰좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        }

        @Test
        void 리뷰에_좋아요를_취소할_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 1L, 리뷰좋아요요청_생성(true));

            // when
            final var response = 리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 1L, 리뷰좋아요요청_생성(false));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        }

        @Test
        void 상품_이미지가_존재하는_좋아요를_가장_많이_받은_리뷰로_상품_이미지가_바뀐다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 2L, 리뷰좋아요요청_생성(true));

            // when
            final var response = 상품_상세_조회_요청(1L);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            상품_사진을_검증한다(response, "2.png");
        }
    }

    @Nested
    class toggleLikeReview_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰에_좋아요를_할때_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // when
            final var response = 리뷰_좋아요_요청(cookie, 1L, 1L, 리뷰좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_리뷰에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));

            // when
            final var response = 리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 1L, 리뷰좋아요요청_생성(null));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 존재하지_않는_리뷰에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));

            // when
            final var response = 리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 99999L, 리뷰좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REVIEW_NOT_FOUND.getCode(), REVIEW_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getSortingReviews_성공_테스트 {

        @Nested
        class 좋아요_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 좋아요_수가_서로_다르면_좋아요_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));
                리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 3L, 리뷰좋아요요청_생성(true));
                리뷰_좋아요_요청(로그인_쿠키를_얻는다(2L), 1L, 2L, 리뷰좋아요요청_생성(true));
                리뷰_좋아요_요청(로그인_쿠키를_얻는다(3L), 1L, 2L, 리뷰좋아요요청_생성(true));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 좋아요수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(2L, 3L, 1L), pageDto);
            }

            @Test
            void 좋아요_수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 좋아요수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L), pageDto);
            }
        }

        @Nested
        class 평점_기준_오름차순으로_리뷰_목록을_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(2L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(1L, 3L, 2L), pageDto);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var page = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L), page);
            }
        }

        @Nested
        class 평점_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(2L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(2L, 3L, 1L), pageDto);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L), pageDto);
            }
        }

        @Nested
        class 최신순으로_리뷰_목록을_조회 {

            @Test
            void 등록_시간이_서로_다르면_최신순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(2L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 1L, 최신순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L), pageDto);
            }
        }
    }

    @Nested
    class getSortingReviews_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰_목록을_조회시_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(2L, List.of(1L)));

            // when
            final var response = 정렬된_리뷰_목록_조회_요청(cookie, 1L, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 존재하지_않는_상품의_리뷰_목록을_조회시_예외가_발생한다() {
            // given && when
            final var response = 정렬된_리뷰_목록_조회_요청(로그인_쿠키를_얻는다(1L), 9999L, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getRankingReviews_성공_테스트 {

        @Test
        void 리뷰_랭킹을_조회하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));

            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(4L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키를_얻는다(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매O_생성(3L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키를_얻는다(1L), 2L, 사진_명세_요청("4"), 리뷰추가요청_재구매O_생성(5L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키를_얻는다(2L), 2L, 사진_명세_요청("5"), 리뷰추가요청_재구매O_생성(1L, List.of(1L)));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 2L, 리뷰좋아요요청_생성(true));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(2L), 1L, 2L, 리뷰좋아요요청_생성(true));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(3L), 1L, 2L, 리뷰좋아요요청_생성(true));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 3L, 리뷰좋아요요청_생성(true));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(2L), 1L, 3L, 리뷰좋아요요청_생성(true));
            리뷰_좋아요_요청(로그인_쿠키를_얻는다(1L), 1L, 4L, 리뷰좋아요요청_생성(true));

            // when
            final var response = 리뷰_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            리뷰_랭킹_조회_결과를_검증한다(response, List.of(2L, 3L, 4L));
        }
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

    private void 정렬된_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> reviewIds,
                                       final PageDto pageDto) {
        페이지를_검증한다(response, pageDto);
        리뷰_목록을_검증한다(response, reviewIds);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<Long> reviewIds) {
        final var actual = response.jsonPath().getList("reviews", SortingReviewDto.class);

        assertThat(actual).extracting(SortingReviewDto::getId)
                .containsExactlyElementsOf(reviewIds);
    }

    private void 리뷰_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> reviewIds) {
        final var actual = response.jsonPath()
                .getList("reviews", RankingReviewDto.class);

        assertThat(actual).extracting(RankingReviewDto::getReviewId)
                .containsExactlyElementsOf(reviewIds);
    }

    private void 상품_사진을_검증한다(final ExtractableResponse<Response> response, final String expected) {
        final var actual = response.jsonPath()
                .getString("image");

        assertThat(actual).isEqualTo(expected);
    }
}

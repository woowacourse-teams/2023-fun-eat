package com.funeat.acceptance.review;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.review.dto.RankingReviewDto;
import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewDetailResponse;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.tag.dto.TagDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.List;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.common.CommonSteps.페이지를_검증한다;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_랭킹_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_상세_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.여러명이_리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.정렬된_리뷰_목록_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.좋아요를_제일_많이_받은_리뷰_조회_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.ImageFixture.이미지1;
import static com.funeat.fixture.ImageFixture.이미지2;
import static com.funeat.fixture.ImageFixture.이미지3;
import static com.funeat.fixture.ImageFixture.이미지4;
import static com.funeat.fixture.ImageFixture.이미지5;
import static com.funeat.fixture.MemberFixture.멤버1;
import static com.funeat.fixture.MemberFixture.멤버2;
import static com.funeat.fixture.MemberFixture.멤버3;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.마지막페이지O;
import static com.funeat.fixture.PageFixture.응답_페이지_생성;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.첫페이지O;
import static com.funeat.fixture.PageFixture.총_데이터_개수;
import static com.funeat.fixture.PageFixture.총_페이지;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.평점_내림차순;
import static com.funeat.fixture.PageFixture.평점_오름차순;
import static com.funeat.fixture.ProductFixture.상품1;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.존재하지_않는_상품;
import static com.funeat.fixture.ReviewFixture.리뷰;
import static com.funeat.fixture.ReviewFixture.리뷰1;
import static com.funeat.fixture.ReviewFixture.리뷰2;
import static com.funeat.fixture.ReviewFixture.리뷰3;
import static com.funeat.fixture.ReviewFixture.리뷰4;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.재구매O;
import static com.funeat.fixture.ReviewFixture.존재하지_않는_리뷰;
import static com.funeat.fixture.ReviewFixture.좋아요O;
import static com.funeat.fixture.ReviewFixture.좋아요X;
import static com.funeat.fixture.ScoreFixture.점수_1점;
import static com.funeat.fixture.ScoreFixture.점수_2점;
import static com.funeat.fixture.ScoreFixture.점수_3점;
import static com.funeat.fixture.ScoreFixture.점수_4점;
import static com.funeat.fixture.ScoreFixture.점수_5점;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeReview_성공_테스트 {

        @Test
        void 이미지를_포함하여_리뷰를_작성한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1),
                    리뷰추가요청_재구매O_생성(점수_1점, List.of(태그)));

            // then
            STATUS_CODE를_검증한다(응답, 정상_생성);
        }

        @Test
        void 이미지가_없어도_리뷰를_작성할_수_있다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, null,
                    리뷰추가요청_재구매O_생성(점수_1점, List.of(태그)));

            // then
            STATUS_CODE를_검증한다(응답, 정상_생성);
        }
    }

    @Nested
    class writeReview_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰_작성시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var 응답 = 리뷰_작성_요청(cookie, 상품, 사진_명세_요청(이미지1),
                    리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_NULL일시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1),
                    리뷰추가요청_재구매O_생성(점수_4점, null));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "태그 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_비어있을시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1),
                    리뷰추가요청_재구매O_생성(점수_4점, Collections.emptyList()));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "적어도 1개의 태그 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_평점이_비어있을시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1),
                    리뷰추가요청_재구매O_생성(null, List.of(태그)));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "평점을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_리뷰_작성할때_리뷰내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var 요청 = 리뷰추가요청_생성(점수_1점, List.of(태그), content, 재구매O);

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "리뷰 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_재구매여부가_비어있을시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var 요청 = 리뷰추가요청_생성(점수_1점, List.of(태그), "content", null);

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "재구매 여부를 입력해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_리뷰_작성할때_리뷰내용이_200자_초과시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var 리뷰내용_201자 = "test".repeat(50) + "a";
            final var 요청 = new ReviewCreateRequest(점수_1점, List.of(태그), 리뷰내용_201자, 재구매O);

            // when
            final var 응답 = 리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "리뷰 내용은 최대 200자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class toggleLikeReview_성공_테스트 {

        @Test
        void 리뷰에_좋아요를_할_수_있다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 리뷰, 리뷰좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }

        @Test
        void 리뷰에_좋아요를_취소할_수_있다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
            리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 리뷰, 리뷰좋아요요청_생성(좋아요O));

            // when
            final var 응답 = 리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 리뷰, 리뷰좋아요요청_생성(좋아요X));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }

        @Test
        void 상품_이미지가_존재하는_좋아요를_가장_많이_받은_리뷰로_상품_이미지가_바뀐다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_1점, List.of(태그)));

            리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 리뷰2, 리뷰좋아요요청_생성(좋아요O));

            // when
            final var 응답 = 상품_상세_조회_요청(상품);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_사진을_검증한다(응답, "2.png");
        }
    }

    @Nested
    class toggleLikeReview_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰에_좋아요를_할때_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_좋아요_요청(cookie, 상품, 리뷰, 리뷰좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_리뷰에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));

            // when
            final var 응답 = 리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 리뷰, 리뷰좋아요요청_생성(null));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 존재하지_않는_리뷰에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));

            // when
            final var 응답 = 리뷰_좋아요_요청(로그인_쿠키_획득(멤버1), 상품, 존재하지_않는_리뷰, 리뷰좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REVIEW_NOT_FOUND.getCode(), REVIEW_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getSortingReviews_성공_테스트 {

        @Nested
        class 좋아요_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 좋아요_수가_서로_다르면_좋아요_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));
                여러명이_리뷰_좋아요_요청(List.of(멤버1), 상품, 리뷰3, 좋아요O);
                여러명이_리뷰_좋아요_요청(List.of(멤버2, 멤버3), 상품, 리뷰2, 좋아요O);

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 좋아요수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰2, 리뷰3, 리뷰1));
            }

            @Test
            void 좋아요_수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 좋아요수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰3, 리뷰2, 리뷰1));
            }
        }

        @Nested
        class 평점_기준_오름차순으로_리뷰_목록을_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_2점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰1, 리뷰3, 리뷰2));
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰3, 리뷰2, 리뷰1));
            }
        }

        @Nested
        class 평점_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_2점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰2, 리뷰3, 리뷰1));
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰3, 리뷰2, 리뷰1));
            }
        }

        @Nested
        class 최신순으로_리뷰_목록을_조회 {

            @Test
            void 등록_시간이_서로_다르면_최신순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_즉석조리_생성();
                단일_카테고리_저장(카테고리);
                final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_2점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 상품, 최신순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                정렬된_리뷰_목록_조회_결과를_검증한다(응답, List.of(리뷰3, 리뷰2, 리뷰1));
            }
        }
    }

    @Nested
    class getSortingReviews_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_리뷰_목록을_조회시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_2점, List.of(태그)));

            // when
            final var 응답 = 정렬된_리뷰_목록_조회_요청(cookie, 상품, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 존재하지_않는_상품의_리뷰_목록을_조회시_예외가_발생한다() {
            // given & when
            final var 응답 = 정렬된_리뷰_목록_조회_요청(로그인_쿠키_획득(멤버1), 존재하지_않는_상품, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getRankingReviews_성공_테스트 {

        @Test
        void 리뷰_랭킹을_조회하다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품1, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품1, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품1, 사진_명세_요청(이미지3), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품2, 사진_명세_요청(이미지4), 리뷰추가요청_재구매O_생성(점수_5점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품2, 사진_명세_요청(이미지5), 리뷰추가요청_재구매O_생성(점수_1점, List.of(태그)));
            여러명이_리뷰_좋아요_요청(List.of(멤버1, 멤버2, 멤버3), 상품1, 리뷰2, 좋아요O);
            여러명이_리뷰_좋아요_요청(List.of(멤버1, 멤버2), 상품1, 리뷰3, 좋아요O);
            여러명이_리뷰_좋아요_요청(List.of(멤버1), 상품1, 리뷰4, 좋아요O);

            // when
            final var 응답 = 리뷰_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            리뷰_랭킹_조회_결과를_검증한다(응답, List.of(리뷰2, 리뷰3, 리뷰4));
        }
    }

    @Nested
    class getMostFavoriteReview_성공_테스트 {

        @Test
        void 특정_상품에서_좋아요를_가장_많이_받은_리뷰를_조회하다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매O_생성(점수_4점, List.of(태그)));
            여러명이_리뷰_좋아요_요청(List.of(멤버1, 멤버2, 멤버3), 상품1, 리뷰2, 좋아요O);

            // when
            final var 응답 = 좋아요를_제일_많이_받은_리뷰_조회_요청(상품);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            좋아요를_제일_많이_받은_리뷰_결과를_검증한다(응답, 리뷰2);
        }

        @Test
        void 특정_상품에서_리뷰가_없다면_빈_응답을_반환하다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));

            // when
            final var 응답 = 좋아요를_제일_많이_받은_리뷰_조회_요청(상품);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
            좋아요를_제일_많이_받은_리뷰_결과가_빈_응답인지_검증한다(응답);
        }
    }

    @Nested
    class getMostFavoriteReview_실패_테스트 {

        @Test
        void 존재하지_않는_상품의_좋아요를_가장_많이_받은_리뷰_조회시_예외가_발생한다() {
            // given & when
            final var 응답 = 좋아요를_제일_많이_받은_리뷰_조회_요청(존재하지_않는_상품);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getReviewDetail_성공_테스트 {

        @Test
        void 리뷰_상세_정보를_조회한다() {
            // given
            final var 카테고리 = 카테고리_즉석조리_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var 요청 = 리뷰추가요청_재구매O_생성(점수_3점, List.of(태그));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 요청);

            // when
            final var 응답 = 리뷰_상세_조회_요청(리뷰);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            리뷰_상세_정보_조회_결과를_검증한다(응답, 요청);
        }
    }

    @Nested
    class getReviewDetail_실패_테스트 {

        @Test
        void 존재하지_않는_리뷰_조회시_예외가_발생한다() {
            // given & when
            final var 응답 = 리뷰_상세_조회_요청(존재하지_않는_리뷰);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REVIEW_NOT_FOUND.getCode(), REVIEW_NOT_FOUND.getMessage());
        }
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

    private void 정렬된_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> reviewIds) {
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

    private void 좋아요를_제일_많이_받은_리뷰_결과를_검증한다(final ExtractableResponse<Response> response, final Long reviewId) {
        final var actual = response.jsonPath()
                .getLong("id");

        assertThat(actual).isEqualTo(reviewId);
    }

    private void 좋아요를_제일_많이_받은_리뷰_결과가_빈_응답인지_검증한다(final ExtractableResponse<Response> response) {
        final var actual = response.body()
                .asString();

        assertThat(actual).isEmpty();
    }

    private void 상품_사진을_검증한다(final ExtractableResponse<Response> response, final String expected) {
        final var actual = response.jsonPath()
                .getString("image");

        assertThat(actual).isEqualTo(expected);
    }

    private void 리뷰_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response, final ReviewCreateRequest request) {
        final var actual = response.as(ReviewDetailResponse.class);
        final var actualTags = response.jsonPath().getList("tags", TagDto.class);

        assertSoftly(soft -> {
            soft.assertThat(actual.getId()).isEqualTo(리뷰);
            soft.assertThat(actual.getImage()).isEqualTo("1.png");
            soft.assertThat(actual.getRating()).isEqualTo(request.getRating());
            soft.assertThat(actual.getContent()).isEqualTo(request.getContent());
            soft.assertThat(actual.isRebuy()).isEqualTo(request.getRebuy());
            soft.assertThat(actual.getFavoriteCount()).isEqualTo(0L);
            soft.assertThat(actualTags).extracting(TagDto::getId)
                    .containsExactlyElementsOf(request.getTagIds());
        });
    }
}

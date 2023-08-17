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
import static com.funeat.acceptance.review.ReviewSteps.단일_리뷰_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_랭킹_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.정렬된_리뷰_목록_조회_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_false_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_true_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_푸짐해요_PRICE_생성;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.presentation.dto.RankingReviewDto;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.tag.domain.Tag;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }

        @Test
        void 이미지가_없어도_리뷰를_작성할_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 단일_리뷰_요청(productId, null, request, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }

    @Nested
    class writeReview_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_리뷰_작성시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);

            // when
            final var response = 단일_리뷰_요청(productId, image, request, null);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_NULL일시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = 리뷰추가요청_재구매O_생성(4L, null);
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "태그 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰_작성할때_태그들이_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = 리뷰추가요청_재구매O_생성(4L, Collections.emptyList());
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "적어도 1개의 태그 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰_작성할때_평점이_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = 리뷰추가요청_재구매O_생성(null, tagIds);
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "평점을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_리뷰_작성할때_리뷰내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = new ReviewCreateRequest(1L, tagIds, content, true);
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "리뷰 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰_작성할때_재구매여부가_비어있을시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = new ReviewCreateRequest(1L, tagIds, "content", null);
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "재구매 여부를 입력해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰_작성할때_리뷰내용이_200자_초과시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var maxContent = "test".repeat(50) + "a";
            final var request = new ReviewCreateRequest(1L, tagIds, maxContent, true);
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "리뷰 내용은 최대 200자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }
    }

    @Nested
    class toggleLikeReview_성공_테스트 {

        @Test
        void 리뷰에_좋아요를_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();
            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();
            final var favoriteRequest = 리뷰좋아요요청_true_생성();

            // when
            final var response = 리뷰_좋아요_요청(productId, reviewId, favoriteRequest, loginCookie);
            final var actual = reviewFavoriteRepository.findAll().get(0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            리뷰_좋아요_결과를_검증한다(actual, memberId, reviewId, true);
        }

        @Test
        void 리뷰에_좋아요를_취소할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();

            final var favoriteRequest = 리뷰좋아요요청_true_생성();
            리뷰_좋아요_요청(productId, reviewId, favoriteRequest, loginCookie);

            final var favoriteCancelRequest = 리뷰좋아요요청_false_생성();

            // when
            final var response = 리뷰_좋아요_요청(productId, reviewId, favoriteCancelRequest, loginCookie);
            final var actual = reviewFavoriteRepository.findAll().get(0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            리뷰_좋아요_결과를_검증한다(actual, memberId, reviewId, false);
        }

        @Test
        void 가장_좋아요를_많이_받은_리뷰가_존재하면_상품_이미지가_바뀐다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var firstImage = 사진_명세_요청("first");
            final var secondImage = 사진_명세_요청("second");
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            단일_리뷰_요청(productId, firstImage, reviewRequest, loginCookie);
            단일_리뷰_요청(productId, secondImage, reviewRequest, loginCookie);

            final var firstReview = reviewRepository.findById(1L).get();
            final var firstReviewId = firstReview.getId();
            final var secondReview = reviewRepository.findById(2L).get();
            final var secondReviewId = secondReview.getId();

            final var trueFavoriteRequest = 리뷰좋아요요청_true_생성();
            리뷰_좋아요_요청(productId, secondReviewId, trueFavoriteRequest, loginCookie);
            final var falseFavoriteRequest = 리뷰좋아요요청_false_생성();
            리뷰_좋아요_요청(productId, secondReviewId, falseFavoriteRequest, loginCookie);

            final var expected = reviewRepository.findAll().get(0);

            // when
            final var response = 리뷰_좋아요_요청(productId, firstReviewId, trueFavoriteRequest, loginCookie);
            final var actual = productRepository.findAll().get(0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            상품_사진을_검증한다(actual, expected);
        }
    }

    @Nested
    class toggleLikeReview_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_리뷰에_좋아요를_할때_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();
            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();
            final var favoriteRequest = 리뷰좋아요요청_true_생성();

            // when
            final var response = 리뷰_좋아요_요청(productId, reviewId, favoriteRequest, null);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_리뷰에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();
            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();

            // when
            final var request = new ReviewFavoriteRequest(null);
            final var response = 리뷰_좋아요_요청(productId, reviewId, request, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 존재하지_않는_리뷰에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var favoriteRequest = 리뷰좋아요요청_true_생성();
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var notExistReviewId = 99999L;
            final var response = 리뷰_좋아요_요청(productId, notExistReviewId, favoriteRequest, loginCookie);

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

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review2, review3, review1);
                final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "favoriteCount,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, pageDto, member1);
            }

            @Test
            void 좋아요_수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 130L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 130L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "favoriteCount,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, pageDto, member1);
            }
        }

        @Nested
        class 평점_기준_오름차순으로_리뷰_목록을_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review1, review3, review2);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,asc", 0);
                final var page = new PageDto(3L, 1L, true, true, 0L, 10L);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var page = new PageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }

        @Nested
        class 평점_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review2, review3, review1);
                final var page = new PageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var page = new PageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }

        @Nested
        class 최신순으로_리뷰_목록을_조회 {

            @Test
            void 등록_시간이_서로_다르면_최신순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "createdAt,desc", 0);
                final var page = new PageDto(3L, 1L, true, true, 0L, 10L);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }
    }

    @Nested
    class getSortingReviews_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_리뷰_목록을_조회시_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            // when
            final var response = 정렬된_리뷰_목록_조회_요청(null, productId, "favoriteCount,desc", 0);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 존재하지_않는_상품의_리뷰_목록을_조회시_예외가_발생한다() {
            // given
            final var notExistProductId = 99999L;
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, notExistProductId, "favoriteCount,desc", 0);

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

            final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 5L);
            final var review1_2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product1, 351L);
            final var review1_3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product1, 130L);
            final var review2_1 = 리뷰_이미지test5_평점5점_재구매O_생성(member1, product2, 247L);
            final var review2_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member2, product2, 83L);
            복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2);

            final var rankingReviews = List.of(review1_2, review2_1, review1_3);

            // when
            final var response = 리뷰_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            리뷰_랭킹_조회_결과를_검증한다(response, rankingReviews);
        }
    }

    private void 리뷰_좋아요_결과를_검증한다(final ReviewFavorite actual, final Long expectedMemberId,
                                 final Long expectedReviewId, final Boolean expectedFavorite) {
        final var actualId = actual.getId();
        final var actualMemberId = actual.getMember().getId();
        final var actualReviewId = actual.getReview().getId();
        final var actualFavorite = actual.getFavorite();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualId)
                    .isNotNull();
            softAssertions.assertThat(actualReviewId)
                    .isEqualTo(expectedReviewId);
            softAssertions.assertThat(actualMemberId)
                    .isEqualTo(expectedMemberId);
            softAssertions.assertThat(actualFavorite)
                    .isEqualTo(expectedFavorite);
        });
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

    private List<Long> 태그_아이디_변환(final Tag... tags) {
        return Stream.of(tags)
                .map(Tag::getId)
                .collect(Collectors.toList());
    }

    private void 정렬된_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                                       final PageDto pageDto, final Member member) {
        페이지를_검증한다(response, pageDto);
        리뷰_목록을_검증한다(response, reviews, member);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                             final Member member) {
        final var expected = reviews.stream()
                .map(review -> SortingReviewDto.toDto(review, member))
                .collect(Collectors.toList());
        final var actual = response.jsonPath().getList("reviews", SortingReviewDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 리뷰_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews) {
        final var expected = reviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("reviews", RankingReviewDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 상품_사진을_검증한다(final Product product, final Review review) {
        final var actual = product.getImage();
        final var expected = review.getImage();

        assertThat(actual).isEqualTo(expected);
    }
}

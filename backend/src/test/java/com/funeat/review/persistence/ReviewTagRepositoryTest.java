package com.funeat.review.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점2점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewTagRepositoryTest extends RepositoryTest {

    @Nested
    class findTop3TagsByReviewIn_성공_테스트 {

        @Test
        void 리뷰_목록에서_상위_3개에_해당하는_태그를_조회한다() {

            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격3000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_단짠단짠_TASTE_생성();
            final var tag3 = 태그_갓성비_PRICE_생성();
            final var tag4 = 태그_간식_ETC_생성();
            복수_태그_저장(tag1, tag2, tag3, tag4);

            final var review1 = 리뷰_이미지test5_평점5점_재구매O_생성(member, product, 0L);
            final var review2 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 0L);
            final var review3 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
            복수_리뷰_저장(review1, review2, review3);

            final var reviewTag1_1 = 리뷰_태그_생성(review1, tag1);
            final var reviewTag1_2 = 리뷰_태그_생성(review1, tag2);
            final var reviewTag2_1 = 리뷰_태그_생성(review2, tag1);
            final var reviewTag2_2 = 리뷰_태그_생성(review2, tag2);
            final var reviewTag2_3 = 리뷰_태그_생성(review2, tag3);
            final var reviewTag3_1 = 리뷰_태그_생성(review3, tag1);
            복수_리뷰_태그_저장(reviewTag1_1, reviewTag1_2, reviewTag2_1, reviewTag2_2, reviewTag2_3, reviewTag3_1);

            final var page = 페이지요청_기본_생성(0, 3);

            final var expected = List.of(tag1, tag2, tag3);

            // when
            final var top3Tags = reviewTagRepository.findTop3TagsByReviewIn(productId, page);

            // then
            assertThat(top3Tags).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class deleteByReview_성공_테스트 {

        @Test
        void 해당_리뷰에_달린_태그를_삭제할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격3000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_단짠단짠_TASTE_생성();
            final var tag3 = 태그_갓성비_PRICE_생성();
            final var tag4 = 태그_간식_ETC_생성();
            복수_태그_저장(tag1, tag2, tag3, tag4);

            final var review1 = 리뷰_이미지test5_평점5점_재구매O_생성(member, product, 0L);
            final var review2 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 0L);
            final var review3 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
            복수_리뷰_저장(review1, review2, review3);

            final var reviewTag1_1 = 리뷰_태그_생성(review1, tag1);
            final var reviewTag1_2 = 리뷰_태그_생성(review1, tag2);
            final var reviewTag2_1 = 리뷰_태그_생성(review2, tag1);
            final var reviewTag2_2 = 리뷰_태그_생성(review2, tag2);
            final var reviewTag2_3 = 리뷰_태그_생성(review2, tag3);
            final var reviewTag3_1 = 리뷰_태그_생성(review3, tag1);
            복수_리뷰_태그_저장(reviewTag1_1, reviewTag1_2, reviewTag2_1, reviewTag2_2, reviewTag2_3, reviewTag3_1);

            final var expected = List.of(reviewTag2_1, reviewTag2_2, reviewTag2_3, reviewTag3_1);

            // when
            reviewTagRepository.deleteByReview(review1);

            // then
            final var remainings = reviewTagRepository.findAll();
            assertThat(remainings).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    private ReviewTag 리뷰_태그_생성(final Review review, final Tag tag) {
        return ReviewTag.createReviewTag(review, tag);
    }
}

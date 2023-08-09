package com.funeat.review.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점_2점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewTagRepositoryTest extends RepositoryTest {

    @Nested
    class findTop3TagsByReviewIn_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 리뷰_목록에서_상위_3개에_해당하는_태그를_조회한다() {

                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product = 상품_삼각김밥_가격3000원_평점_2점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var tag1 = 태그_맛있어요_TASTE_생성();
                final var tag2 = 태그_단짠단짠_TASTE_생성();
                final var tag3 = 태그_갓성비_PRICE_생성();
                final var tag4 = 태그_간식_ETC_생성();
                final var tags = List.of(tag1, tag2, tag3, tag4);
                복수_태그_저장(tags);

                final var review1 = 리뷰_이미지test5_평점5점_재구매O_생성(member, product, 0L);
                final var review2 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 0L);
                final var review3 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
                final var reviews = List.of(review1, review2, review3);
                복수_리뷰_저장(reviews);

                final var reviewTag1_1 = 리뷰_태그_생성(tag1, review1);
                final var reviewTag1_2 = 리뷰_태그_생성(tag2, review1);
                final var reviewTag2_1 = 리뷰_태그_생성(tag1, review2);
                final var reviewTag2_2 = 리뷰_태그_생성(tag2, review2);
                final var reviewTag2_3 = 리뷰_태그_생성(tag3, review2);
                final var reviewTag3_1 = 리뷰_태그_생성(tag1, review2);
                final var reviewTags = List.of(reviewTag1_1, reviewTag1_2, reviewTag2_1, reviewTag2_2, reviewTag2_3,
                        reviewTag3_1);
                복수_리뷰_태그_저장(reviewTags);

                final var page = 페이지요청_기본_생성(0, 3);

                final var expected = List.of(tag1, tag2, tag3);

                // when
                final var top3Tags = reviewTagRepository.findTop3TagsByReviewIn(productId, page);

                // then
                assertThat(top3Tags).usingRecursiveComparison().isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    private ReviewTag 리뷰_태그_생성(final Tag tag1, final Review review1) {
        return ReviewTag.createReviewTag(review1, tag1);
    }

    private Long 단일_카테고리_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }

    private Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    private void 복수_태그_저장(final List<Tag> tags) {
        tagRepository.saveAll(tags);
    }

    private void 복수_리뷰_저장(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private void 복수_리뷰_태그_저장(final List<ReviewTag> reviewTags) {
        reviewTagRepository.saveAll(reviewTags);
    }
}

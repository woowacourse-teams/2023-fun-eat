package com.funeat.review.persistence;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("NonAsciiCharacters")
class ReviewTagRepositoryTest extends RepositoryTest {

    @Test
    void 리뷰_목록에서_상위_3개에_해당하는_태그를_조회한다() {
        // given
        final var member = new Member("test1", "test1.png", "1");
        단일_멤버_저장(member);

        final var product = new Product("망고", 1_000L, "mango.png", "망고망고", null);
        단일_상품_저장(product);

        final var tag1 = new Tag("1번", TagType.ETC);
        final var tag2 = new Tag("2번", TagType.ETC);
        final var tag3 = new Tag("3번", TagType.ETC);
        final var tag4 = new Tag("4번", TagType.ETC);
        final var tags = List.of(tag1, tag2, tag3, tag4);
        복수_태그_저장(tags);

        final var review1 = new Review(member, product, "review1.png", 5L, "최고의 망고", true, 25L);
        final var review2 = new Review(member, product, "review2.png", 3L, "그럭저럭 망고", false, 10L);
        final var reviews = List.of(review1, review2);
        복수_리뷰_저장(reviews);

        final var reviewTag1 = ReviewTag.createReviewTag(review1, tag1);
        final var reviewTag2 = ReviewTag.createReviewTag(review1, tag2);
        final var reviewTag3 = ReviewTag.createReviewTag(review2, tag2);
        final var reviewTag4 = ReviewTag.createReviewTag(review2, tag3);
        final var reviewTag5 = ReviewTag.createReviewTag(review2, tag3);
        final var reviewTag6 = ReviewTag.createReviewTag(review2, tag1);
        final var reviewTag7 = ReviewTag.createReviewTag(review2, tag4);
        final var reviewTag8 = ReviewTag.createReviewTag(review2, tag2);
        final var reviewTag9 = ReviewTag.createReviewTag(review2, tag2);
        final var reviewTag10 = ReviewTag.createReviewTag(review2, tag1);
        final var reviewTags = List.of(reviewTag1, reviewTag2, reviewTag3, reviewTag4, reviewTag5, reviewTag6,
                reviewTag7, reviewTag8, reviewTag9, reviewTag10);
        복수_리뷰_태그_저장(reviewTags);

        // when
        final var top3Tags = reviewTagRepository.findTop3TagsByReviewIn(product.getId(), PageRequest.of(0, 3));

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(top3Tags).hasSize(3);
            softAssertions.assertThat(top3Tags).usingRecursiveFieldByFieldElementComparator()
                    .containsExactly(tag2, tag1, tag3);
        });
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

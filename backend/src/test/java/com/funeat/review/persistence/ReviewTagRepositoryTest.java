package com.funeat.review.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReviewTagRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 리뷰_목록에서_상위_3개에_해당하는_태그를_조회한다() {
        // given
        final var member = new Member("test1", "test1.png", "1");
        memberRepository.save(member);

        final var product = new Product("망고", 1_000L, "mango.png", "망고망고", null);
        productRepository.save(product);

        final var tag1 = new Tag("1번");
        final var tag2 = new Tag("2번");
        final var tag3 = new Tag("3번");
        final var tag4 = new Tag("4번");
        tagRepository.saveAll(List.of(tag1, tag2, tag3, tag4));

        final var review1 = new Review(member, product, "review1.png", 5L, "최고의 망고", true, 25L);
        final var review2 = new Review(member, product, "review2.png", 3L, "그럭저럭 망고", false, 10L);
        reviewRepository.saveAll(List.of(review1, review2));

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
        reviewTagRepository.saveAll(
                List.of(reviewTag1, reviewTag2, reviewTag3, reviewTag4, reviewTag5, reviewTag6, reviewTag7, reviewTag8,
                        reviewTag9, reviewTag10)
        );

        // when
        final List<Tag> tags = reviewTagRepository.findTop3TagsByReviewIn(product.getId(), PageRequest.of(0, 3));

        // then
        assertThat(tags).hasSize(3);
        assertThat(tags).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(tag2, tag1, tag3);
    }
}

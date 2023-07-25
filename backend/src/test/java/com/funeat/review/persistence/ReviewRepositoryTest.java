package com.funeat.review.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReviewRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 상품에_달린_리뷰의_숫자를_반환한다() {
        // given
        Member member = memberRepository.save(new Member("test", "image.png"));
        Category category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
        Product product1 = productRepository.save(new Product("삼각김밥", 1000L, "image.png", "맛있는 삼각김밥", category));
        Product product2 = productRepository.save(new Product("라면", 2000L, "image.png", "맛있는 라면", category));

        reviewRepository.save(new Review(member, product1, "review.png", 4L, "이 삼각김밥은 최고!!", true));
        reviewRepository.save(new Review(member, product1, "review.png", 3L, "이 삼각김밥은 별로", false));
        reviewRepository.save(new Review(member, product1, "review.png", 4L, "이 삼각김밥은 쏘쏘", true));
        reviewRepository.save(new Review(member, product2, "review.png", 3L, "이 라면은 맛있다", true));

        // when
        assertThat(reviewRepository.countByProduct(product1)).isEqualTo(3);
        assertThat(reviewRepository.countByProduct(product2)).isEqualTo(1);
    }

    @Test
    void 특정_상품에_대한_좋아요_기준_내림차순으로_정렬한다() {
        // given
        final var member1 = new Member("test1", "test1.png");
        final var member2 = new Member("test2", "test2.png");
        final var member3 = new Member("test3", "test3.png");
        final var members = List.of(member1, member2, member3);
        memberRepository.saveAll(members);

        final var product = new Product("김밥", 1000L, "kimbap.png", "우영우가 먹은 그 김밥", null);
        productRepository.save(product);

        final var review1 = new Review(member1, product, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 351L);
        final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 24L);
        final var review3 = new Review(member3, product, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
        final var reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);

        final var expected = List.of(review1, review3);

        final var page = PageRequest.of(0, 2, Sort.by("favoriteCount").descending());

        // when
        final List<Review> actual = reviewRepository.findReviewsByProduct(page, product)
                .getContent();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 전체_리뷰_목록에서_가장_좋아요가_높은_상위_3개의_리뷰를_가져온다() {
        // given

        final var member1 = new Member("test1", "test1.png");
        final var member2 = new Member("test2", "test2.png");
        final var member3 = new Member("test3", "test3.png");
        final var members = List.of(member1, member2, member3);
        memberRepository.saveAll(members);

        final var product1 = new Product("김밥", 1000L, "image.png", "김밥", null);
        final var product2 = new Product("물", 500L, "water.jpg", "물", null);
        final var products = List.of(product1, product2);
        productRepository.saveAll(products);

        final var review1 = new Review(member1, product1, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 5L);
        final var review2 = new Review(member2, product1, "review2.jpg", 4L, "역삼역", true, 351L);
        final var review3 = new Review(member3, product1, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
        final var review4 = new Review(member2, product2, "review4.jpg", 5L, "ㅁㅜㄹ", true, 247L);
        final var review5 = new Review(member3, product2, "review5.jpg", 1L, "ㄴㄴ", false, 83L);
        final var reviews = List.of(review1, review2, review3, review4, review5);
        reviewRepository.saveAll(reviews);

        final var expected = List.of(review2, review4, review3);

        // when
        final var actual = reviewRepository.findTop3ByOrderByFavoriteCountDesc();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}

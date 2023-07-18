package com.funeat.review.persistence;

import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 특정_상품에_대한_좋아요_기준_내림차순으로_정렬한다() {
        // given
        final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
        final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
        final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
        final var members = List.of(member1, member2, member3);
        memberRepository.saveAll(members);

        final var product = new Product("김밥", 1000L, "kimbap.png", "우영우가 먹은 그 김밥", null);
        productRepository.save(product);

        final var review1 = new Review(member1, product, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 351L);
        final var review2 = new Review(member2, product, "review2.jpg", 4.5, "역삼역", true, 24L);
        final var review3 = new Review(member3, product, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
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

        final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
        final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
        final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
        final var members = List.of(member1, member2, member3);
        memberRepository.saveAll(members);

        final var product1 = new Product("김밥", 1000L, "image.png", "김밥", null);
        final var product2 = new Product("물", 500L, "water.jpg", "물", null);
        final var products = List.of(product1, product2);
        productRepository.saveAll(products);

        final var review1 = new Review(member1, product1, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 5L);
        final var review2 = new Review(member2, product1, "review2.jpg", 4.5, "역삼역", true, 351L);
        final var review3 = new Review(member3, product1, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
        final var review4 = new Review(member2, product2, "review4.jpg", 5.0, "ㅁㅜㄹ", true, 247L);
        final var review5 = new Review(member3, product2, "review5.jpg", 1.5, "ㄴㄴ", false, 83L);
        final var reviews = List.of(review1, review2, review3, review4, review5);
        reviewRepository.saveAll(reviews);

        final var expected = List.of(review2, review4, review3);

        // when
        final var actual = reviewRepository.findTop3ByOrderByFavoriteCountDesc();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}

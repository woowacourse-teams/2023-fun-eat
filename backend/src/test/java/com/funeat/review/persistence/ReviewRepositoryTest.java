package com.funeat.review.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
        Member member = memberRepository.save(new Member("test", "image.png", 27, Gender.FEMALE, "01036551086"));
        Category category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
        Product product1 = productRepository.save(new Product("삼각김밥", 1000L, "image.png", "맛있는 삼각김밥", category));
        Product product2 = productRepository.save(new Product("라면", 2000L, "image.png", "맛있는 라면", category));

        reviewRepository.save(new Review(member, product1, "review.png", 4.5, "이 삼각김밥은 최고!!", true));
        reviewRepository.save(new Review(member, product1, "review.png", 3.0, "이 삼각김밥은 별로", false));
        reviewRepository.save(new Review(member, product1, "review.png", 4.0, "이 삼각김밥은 쏘쏘", true));
        reviewRepository.save(new Review(member, product2, "review.png", 3.0, "이 라면은 맛있다", true));

        // when
        assertThat(reviewRepository.countByProduct(product1)).isEqualTo(3);
        assertThat(reviewRepository.countByProduct(product2)).isEqualTo(1);
    }
}

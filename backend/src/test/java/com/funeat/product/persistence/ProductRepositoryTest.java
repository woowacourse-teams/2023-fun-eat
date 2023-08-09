package com.funeat.product.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.페이지요청_가격_내림차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_가격_오름차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.PageFixture.페이지요청_평점_내림차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_평점_오름차순_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점_1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점_4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점_1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점_5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점_1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점_2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점_1점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점2점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점4점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_평점5점_재구매O_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductReviewCountDto;
import com.funeat.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductRepositoryTest extends RepositoryTest {

    @Nested
    class findByAllCategory_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 카테고리별_상품을_평점이_높은_순으로_정렬한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_1점_생성(category);
                final var product2 = 상품_삼각김밥_가격1000원_평점_2점_생성(category);
                final var product3 = 상품_삼각김밥_가격1000원_평점_3점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점_4점_생성(category);
                final var product5 = 상품_삼각김밥_가격1000원_평점_5점_생성(category);
                final var products = List.of(product1, product2, product3, product4, product5);
                복수_상품_저장(products);

                final var page = 페이지요청_평점_내림차순_생성(0, 3);

                final var productInCategoryDto1 = ProductInCategoryDto.toDto(product5, 0L);
                final var productInCategoryDto2 = ProductInCategoryDto.toDto(product4, 0L);
                final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
                final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

                // when
                final var actual = productRepository.findAllByCategory(category, page).getContent();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }

            @Test
            void 카테고리별_상품을_평점이_낮은_순으로_정렬한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_1점_생성(category);
                final var product2 = 상품_삼각김밥_가격1000원_평점_2점_생성(category);
                final var product3 = 상품_삼각김밥_가격1000원_평점_3점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점_4점_생성(category);
                final var product5 = 상품_삼각김밥_가격1000원_평점_5점_생성(category);
                final var products = List.of(product1, product2, product3, product4, product5);
                복수_상품_저장(products);

                final var page = 페이지요청_평점_오름차순_생성(0, 3);

                final var productInCategoryDto1 = ProductInCategoryDto.toDto(product1, 0L);
                final var productInCategoryDto2 = ProductInCategoryDto.toDto(product2, 0L);
                final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
                final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

                // when
                final var actual = productRepository.findAllByCategory(category, page).getContent();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }

            @Test
            void 카테고리별_상품을_가격이_높은_순으로_정렬한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_1점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점_1점_생성(category);
                final var product3 = 상품_삼각김밥_가격3000원_평점_1점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점_1점_생성(category);
                final var product5 = 상품_삼각김밥_가격5000원_평점_1점_생성(category);
                final var products = List.of(product1, product2, product3, product4, product5);
                복수_상품_저장(products);

                final var page = 페이지요청_가격_내림차순_생성(0, 3);

                final var productInCategoryDto1 = ProductInCategoryDto.toDto(product5, 0L);
                final var productInCategoryDto2 = ProductInCategoryDto.toDto(product4, 0L);
                final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
                final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

                // when
                final var actual = productRepository.findAllByCategory(category, page).getContent();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }

            @Test
            void 카테고리별_상품을_가격이_낮은_순으로_정렬한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_1점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점_1점_생성(category);
                final var product3 = 상품_삼각김밥_가격3000원_평점_1점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점_1점_생성(category);
                final var product5 = 상품_삼각김밥_가격5000원_평점_1점_생성(category);
                final var products = List.of(product1, product2, product3, product4, product5);
                복수_상품_저장(products);

                final var page = 페이지요청_가격_오름차순_생성(0, 3);

                final var productInCategoryDto1 = ProductInCategoryDto.toDto(product1, 0L);
                final var productInCategoryDto2 = ProductInCategoryDto.toDto(product2, 0L);
                final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
                final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

                // when
                final var actual = productRepository.findAllByCategory(category, page).getContent();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    @Nested
    class findAllByCategoryOrderByReviewCountDesc_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 카테고리별_상품을_리뷰수가_많은_순으로_정렬한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_1점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점_1점_생성(category);
                final var product3 = 상품_삼각김밥_가격3000원_평점_1점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점_1점_생성(category);
                final var products = List.of(product1, product2, product3, product4);
                복수_상품_저장(products);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                final var members = List.of(member1, member2, member3);
                복수_멤버_저장(members);

                final var review1_1 = 리뷰_평점1점_재구매X_생성(member1, product1);
                final var review1_2 = 리뷰_평점3점_재구매O_생성(member2, product1);
                final var review2_1 = 리뷰_평점4점_재구매O_생성(member3, product2);
                final var review2_2 = 리뷰_평점2점_재구매X_생성(member1, product2);
                final var review2_3 = 리뷰_평점3점_재구매O_생성(member2, product2);
                final var review3_1 = 리뷰_평점3점_재구매O_생성(member1, product3);
                final var reviews = List.of(review1_1, review1_2, review2_1, review2_2, review2_3, review3_1);
                복수_리뷰_저장(reviews);

                final var page = 페이지요청_기본_생성(0, 3);

                final var productInCategoryDto1 = ProductInCategoryDto.toDto(product2, 3L);
                final var productInCategoryDto2 = ProductInCategoryDto.toDto(product1, 2L);
                final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 1L);
                final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

                // when
                final var actual = productRepository.findAllByCategoryOrderByReviewCountDesc(category, page)
                        .getContent();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    @Nested
    class findAllByAverageRatingGreaterThan3_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 평점이_3보다_큰_모든_상품들과_리뷰_수를_조회한다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점_3점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점_4점_생성(category);
                final var product3 = 상품_삼각김밥_가격3000원_평점_5점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점_2점_생성(category);
                final var products = List.of(product1, product2, product3, product4);
                복수_상품_저장(products);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                final var members = List.of(member1, member2, member3);
                복수_멤버_저장(members);

                final var review1_1 = 리뷰_평점1점_재구매X_생성(member1, product1);
                final var review1_2 = 리뷰_평점5점_재구매O_생성(member2, product1);
                final var review2_1 = 리뷰_평점3점_재구매O_생성(member3, product2);
                final var review2_2 = 리뷰_평점4점_재구매X_생성(member1, product2);
                final var review2_3 = 리뷰_평점5점_재구매O_생성(member2, product2);
                final var review3_1 = 리뷰_평점5점_재구매O_생성(member1, product3);
                final var reviews = List.of(review1_1, review1_2, review2_1, review2_2, review2_3, review3_1);
                복수_리뷰_저장(reviews);

                final var productReviewCountDto1 = new ProductReviewCountDto(product2, 3L);
                final var productReviewCountDto2 = new ProductReviewCountDto(product3, 1L);
                final var expected = List.of(productReviewCountDto1, productReviewCountDto2);

                // when
                final var actual = productRepository.findAllByAverageRatingGreaterThan3();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    private void 복수_상품_저장(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private void 단일_카테고리_저장(final Category category) {
        categoryRepository.save(category).getId();
    }

    private void 복수_리뷰_저장(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private void 복수_멤버_저장(final List<Member> members) {
        memberRepository.saveAll(members);
    }
}

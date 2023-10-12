package com.funeat.product.application;

import com.funeat.common.ServiceTest;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.RankingProductsResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점4점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매X_생성;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest extends ServiceTest {

    @Nested
    class getTop3Products_성공_테스트 {

        @Nested
        class 상품_개수에_대한_테스트 {

            @Test
            void 전체_상품이_하나도_없어도_반환값은_있어야한다() {
                // given
                final var expected = RankingProductsResponse.toResponse(Collections.emptyList());

                // when
                final var actual = productService.getTop3Products();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 전체_상품이_1개_이상_3개_미만이라도_상품이_나와야한다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점5점_생성(category);
                복수_상품_저장(product1, product2);

                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var review1_1 = 리뷰_이미지test5_평점5점_재구매O_생성(member, product1, 0L);
                final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product1, 0L);
                final var review1_3 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product1, 0L);
                final var review1_4 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product1, 0L);
                final var review2_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member, product2, 0L);
                final var review2_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member, product2, 0L);
                복수_리뷰_저장(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2);

                final var rankingProductDto1 = RankingProductDto.toDto(product2);
                final var rankingProductDto2 = RankingProductDto.toDto(product1);
                final var rankingProductDtos = List.of(rankingProductDto1, rankingProductDto2);
                final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                // when
                final var actual = productService.getTop3Products();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 전체_상품_중_랭킹이_높은_상위_3개_상품을_구할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
                final var product3 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점4점_생성(category);
                복수_상품_저장(product1, product2, product3, product4);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1_1 = 리뷰_이미지test5_평점5점_재구매O_생성(member1, product1, 0L);
                final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
                final var review1_3 = 리뷰_이미지test3_평점3점_재구매O_생성(member3, product1, 0L);
                final var review1_4 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
                final var review2_1 = 리뷰_이미지test4_평점4점_재구매O_생성(member1, product2, 0L);
                final var review2_2 = 리뷰_이미지test4_평점4점_재구매O_생성(member1, product2, 0L);
                final var review3_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product3, 0L);
                final var review4_1 = 리뷰_이미지test4_평점4점_재구매X_생성(member1, product4, 0L);
                final var review4_2 = 리뷰_이미지test3_평점3점_재구매X_생성(member1, product4, 0L);
                final var review4_3 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product4, 0L);
                복수_리뷰_저장(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2, review3_1, review4_1,
                        review4_2, review4_3);

                final var rankingProductDto1 = RankingProductDto.toDto(product3);
                final var rankingProductDto2 = RankingProductDto.toDto(product4);
                final var rankingProductDto3 = RankingProductDto.toDto(product2);
                final var rankingProductDtos = List.of(rankingProductDto1, rankingProductDto2, rankingProductDto3);
                final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                // when
                final var actual = productService.getTop3Products();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }
        }

        @Nested
        class 상품_점수에_대한_테스트 {

            @Test
            void 모든_상품의_평점이_3점_미만이면_빈_배열을_반환한다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                복수_상품_저장(product1, product2);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1_1 = 리뷰_이미지test1_평점1점_재구매O_생성(member1, product1, 0L);
                final var review1_2 = 리뷰_이미지test2_평점2점_재구매O_생성(member2, product1, 0L);
                final var review1_3 = 리뷰_이미지test3_평점3점_재구매O_생성(member3, product1, 0L);
                final var review2_1 = 리뷰_이미지test1_평점1점_재구매O_생성(member1, product2, 0L);
                final var review2_2 = 리뷰_이미지test2_평점2점_재구매O_생성(member2, product2, 0L);
                복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2);

                final var expected = RankingProductsResponse.toResponse(Collections.emptyList());

                // when
                final var actual = productService.getTop3Products();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 일부_상품이_평점_3점_이상이면_일부_상품만_반환한다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
                복수_상품_저장(product1, product2);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1_1 = 리뷰_이미지test1_평점1점_재구매O_생성(member1, product1, 0L);
                final var review1_2 = 리뷰_이미지test2_평점2점_재구매O_생성(member2, product1, 0L);
                final var review1_3 = 리뷰_이미지test3_평점3점_재구매O_생성(member3, product1, 0L);
                final var review2_1 = 리뷰_이미지test4_평점4점_재구매X_생성(member3, product2, 0L);
                final var review2_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member3, product2, 0L);
                복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2);

                final var rankingProductDtos = List.of(RankingProductDto.toDto(product2));

                final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                // when
                final var actual = productService.getTop3Products();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }
        }
    }
}

package com.funeat.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductTest {

    @Test
    void 평균_평점_업데이트를_할_수_있다() {
        final var product = new Product("testName", 1000L, "testImage", "testContent", null);
        final var reviewRating1 = 4L;
        final var reviewRating2 = 2L;
        final var reviewCount1 = 1L;
        final var reviewCount2 = 2L;

        product.updateAverageRating(reviewRating1, reviewCount1);
        assertThat(product.getAverageRating()).isEqualTo(4.0);

        product.updateAverageRating(reviewRating2, reviewCount2);
        assertThat(product.getAverageRating()).isEqualTo(3.0);
    }
}
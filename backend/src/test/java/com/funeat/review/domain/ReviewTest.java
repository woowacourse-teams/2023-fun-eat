package com.funeat.review.domain;

import static org.assertj.core.api.Assertions.*;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ReviewTest {

    @Nested
    class isAuthor_성공테스트 {

        @Test
        void 리뷰를_작성한_멤버이면_true를_반환한다() {
            // given
            final var product = new Product("test", 1000L, "image", "content", null);

            final var member = new Member("member1", "one", "1");

            final var review = new Review(member, product, "imgae", 5L, "content", true);

            // when
            final var actual = review.checkAuthor(member);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 리뷰를_작성한_멤버가_아니면_false를_반환한다() {
            // given
            final var product = new Product("test", 1000L, "image", "content", null);

            final var correctMember = new Member("realMember", "one", "1");
            final var wrongMember = new Member("wrongMember", "one", "1");

            final var review = new Review(correctMember, product, "imgae", 5L, "content", true);

            // when
            final var actual = review.checkAuthor(wrongMember);

            // then
            assertThat(actual).isFalse();
        }
    }
}

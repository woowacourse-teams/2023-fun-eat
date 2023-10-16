package com.funeat.review.specification;

import com.funeat.review.domain.Review;
import java.util.Arrays;
import java.util.function.Function;

public enum LongTypeReviewSortSpec {

    FAVORITE_COUNT("favoriteCount", Review::getFavoriteCount),
    RATING("rating", Review::getRating);

    private final String fieldName;
    private final Function<Review, Long> function;

    LongTypeReviewSortSpec(final String fieldName, final Function<Review, Long> function) {
        this.fieldName = fieldName;
        this.function = function;
    }

    public static Long find(final String fieldName, final Review lastReview) {
        return Arrays.stream(LongTypeReviewSortSpec.values())
                .filter(reviewSortSpec -> reviewSortSpec.fieldName.equals(fieldName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .function.apply(lastReview);
    }
}

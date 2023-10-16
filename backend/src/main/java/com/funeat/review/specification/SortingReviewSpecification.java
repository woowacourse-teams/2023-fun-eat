package com.funeat.review.specification;

import static com.funeat.review.exception.ReviewErrorCode.REVIEW_SORTING_OPTION_NOT_FOUND;

import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.exception.ReviewException.ReviewSortingOptionNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public final class SortingReviewSpecification {

    private static final List<String> LOCALDATETIME_TYPE_INCLUDE = List.of("createdAt");
    private static final List<String> LONG_TYPE_INCLUDE = List.of("favoriteCount", "rating");
    private static final String DELIMITER = ",";
    private static final String PRODUCT = "product";
    private static final String ID = "id";
    private static final String ASC = "ASC";

    private SortingReviewSpecification() {
    }

    public static Specification<Review> sortingFirstPageBy(final Product product) {
        return (root, query, criteriaBuilder) -> Specification
                .where(equalsProduct(product))
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<Review> sortingBy(final Product product, final String sortOption,
                                                  final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            final String[] sortFieldSplit = sortOption.split(DELIMITER);
            final String field = sortFieldSplit[0];
            final String sort = sortFieldSplit[1];

            return Specification
                    .where((equalsProduct(product).and(equals(field, lastReview)).and(lessThanLastReviewId(lastReview)))
                            .or(equalsProduct(product).and(lessOrGreaterThan(field, sort, lastReview))))
                    .toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Review> equalsProduct(final Product product) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(product)) {
                return null;
            }

            final Path<Product> productPath = root.get(PRODUCT);

            return criteriaBuilder.equal(productPath, product);
        };
    }

    private static Specification<Review> lessThanLastReviewId(final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(lastReview)) {
                return null;
            }

            final Path<Long> reviewPath = root.get(ID);

            return criteriaBuilder.lessThan(reviewPath, lastReview.getId());
        };
    }

    private static Specification<Review> equals(final String fieldName, final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            if (validateNull(fieldName, lastReview)) {
                return null;
            }

            return checkEquals(fieldName, lastReview, root, criteriaBuilder);
        };
    }

    private static Predicate checkEquals(final String fieldName,
                                         final Review lastReview,
                                         final Root<Review> root,
                                         final CriteriaBuilder criteriaBuilder) {
        if (LOCALDATETIME_TYPE_INCLUDE.contains(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.equal(createdAtPath, lastReviewCreatedAt);
        }
        if (LONG_TYPE_INCLUDE.contains(fieldName)) {
            final Path<Long> reviewPath = root.get(fieldName);
            final Long lastReviewField = LongTypeReviewSortSpec.find(fieldName, lastReview);
            return criteriaBuilder.equal(reviewPath, lastReviewField);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND, fieldName);
    }

    private static Specification<Review> lessOrGreaterThan(final String field, final String sort,
                                                           final Review lastReview) {
        if (ASC.equalsIgnoreCase(sort)) {
            return greaterThan(field, lastReview);
        }
        return lessThan(field, lastReview);
    }

    private static Specification<Review> greaterThan(final String fieldName, final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            if (validateNull(fieldName, lastReview)) {
                return null;
            }

            return checkGreaterThan(fieldName, lastReview, root, criteriaBuilder);
        };
    }

    private static Predicate checkGreaterThan(final String fieldName, final Review lastReview, final Root<Review> root,
                                              final CriteriaBuilder criteriaBuilder) {
        if (LOCALDATETIME_TYPE_INCLUDE.contains(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.greaterThan(createdAtPath, lastReviewCreatedAt);
        }
        if (LONG_TYPE_INCLUDE.contains(fieldName)) {
            final Path<Long> reviewPath = root.get(fieldName);
            final Long lastReviewField = LongTypeReviewSortSpec.find(fieldName, lastReview);
            return criteriaBuilder.greaterThan(reviewPath, lastReviewField);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND, fieldName);
    }

    private static Specification<Review> lessThan(final String fieldName, final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            if (validateNull(fieldName, lastReview)) {
                return null;
            }

            return checkLessThan(fieldName, lastReview, root, criteriaBuilder);
        };
    }

    private static boolean validateNull(final String fieldName, final Review lastReview) {
        return Objects.isNull(fieldName) || Objects.isNull(lastReview);
    }

    private static Predicate checkLessThan(final String fieldName, final Review lastReview, final Root<Review> root,
                                           final CriteriaBuilder criteriaBuilder) {
        if (LOCALDATETIME_TYPE_INCLUDE.contains(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.lessThan(createdAtPath, lastReviewCreatedAt);
        }
        if (LONG_TYPE_INCLUDE.contains(fieldName)) {
            final Path<Long> reviewPath = root.get(fieldName);
            final Long lastReviewField = LongTypeReviewSortSpec.find(fieldName, lastReview);
            return criteriaBuilder.lessThan(reviewPath, lastReviewField);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND, fieldName);
    }
}

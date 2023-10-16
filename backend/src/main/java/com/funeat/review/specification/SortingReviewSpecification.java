package com.funeat.review.specification;

import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public final class SortingReviewSpecification {

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
            final String[] sortFieldSplit = sortOption.split(",");
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

            final Path<Product> productPath = root.get("product");

            return criteriaBuilder.equal(productPath, product);
        };
    }

    private static Specification<Review> lessThanLastReviewId(final Review lastReview) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(lastReview)) {
                return null;
            }

            final Path<Long> reviewPath = root.get("id");

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
        if ("createdAt".equalsIgnoreCase(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.equal(createdAtPath, lastReviewCreatedAt);
        }
        final Path<Long> reviewPath = root.get(fieldName);
        final Long lastReviewField = ReviewSortSpec.find(fieldName, lastReview);
        return criteriaBuilder.equal(reviewPath, lastReviewField);
    }

    private static Specification<Review> lessOrGreaterThan(final String field, final String sort,
                                                           final Review lastReview) {
        if ("ASC".equalsIgnoreCase(sort)) {
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
        if ("createdAt".equalsIgnoreCase(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.greaterThan(createdAtPath, lastReviewCreatedAt);
        }
        final Path<Long> reviewPath = root.get(fieldName);
        final Long lastReviewField = ReviewSortSpec.find(fieldName, lastReview);
        return criteriaBuilder.greaterThan(reviewPath, lastReviewField);
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
        if ("createdAt".equalsIgnoreCase(fieldName)) {
            final Path<LocalDateTime> createdAtPath = root.get(fieldName);
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return criteriaBuilder.lessThan(createdAtPath, lastReviewCreatedAt);
        }
        final Path<Long> reviewPath = root.get(fieldName);
        final Long lastReviewField = ReviewSortSpec.find(fieldName, lastReview);
        return criteriaBuilder.lessThan(reviewPath, lastReviewField);
    }
}

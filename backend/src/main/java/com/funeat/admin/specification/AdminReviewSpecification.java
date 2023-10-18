package com.funeat.admin.specification;

import com.funeat.admin.dto.ReviewSearchCondition;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class AdminReviewSpecification {

    private AdminReviewSpecification() {
    }

    public static Specification<Review> searchBy(final ReviewSearchCondition condition) {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("member", JoinType.LEFT);
                root.fetch("product", JoinType.LEFT);
            }

            criteriaBuilder.desc(root.get("id"));

            return Specification
                    .where(to(condition.getTo()))
                    .and(from(condition.getFrom()))
                    .and(sameProduct(condition.getProductId()))
                    .and(lessThan(condition.getId()))
                    .toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Review> to(final LocalDateTime to) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(to)) {
                return null;
            }

            final Path<LocalDateTime> toPath = root.get("createdAt");

            return criteriaBuilder.lessThanOrEqualTo(toPath, to);
        };
    }

    private static Specification<Review> from(final LocalDateTime from) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(from)) {
                return null;
            }

            final Path<LocalDateTime> fromPath = root.get("createdAt");

            return criteriaBuilder.greaterThanOrEqualTo(fromPath, from);
        };
    }

    private static Specification<Review> sameProduct(final Long productId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(productId)) {
                return null;
            }

            final Path<Product> productPath = root.get("product");

            return criteriaBuilder.equal(productPath, productId);
        };
    }

    private static Specification<Review> lessThan(final Long reviewId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(reviewId)) {
                return null;
            }

            final Path<Long> reviewIdPath = root.get("id");

            return criteriaBuilder.lessThan(reviewIdPath, reviewId);
        };
    }
}

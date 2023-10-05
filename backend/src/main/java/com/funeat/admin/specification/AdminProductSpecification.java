package com.funeat.admin.specification;

import com.funeat.admin.dto.ProductSearchCondition;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import java.util.Objects;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class AdminProductSpecification {

    public static Specification<Product> searchBy(final ProductSearchCondition condition) {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("category", JoinType.LEFT);
            }

            criteriaBuilder.desc(root.get("id"));

            return Specification
                    .where(like(condition.getName()))
                    .and(lessThan(condition.getId()))
                    .and(sameCategory(condition.getCategoryId()))
                    .toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Product> lessThan(final Long productId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(productId)) {
                return null;
            }

            final Path<Long> productIdPath = root.get("id");

            return criteriaBuilder.lessThan(productIdPath, productId);
        };
    }

    private static Specification<Product> like(final String productName) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(productName)) {
                return null;
            }

            final Path<String> namePath = root.get("name");

            return criteriaBuilder.like(namePath, "%" + productName + "%");
        };
    }

    private static Specification<Product> sameCategory(final Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(categoryId)) {
                return null;
            }

            final Path<Category> categoryPath = root.get("category");

            return criteriaBuilder.equal(categoryPath, categoryId);
        };
    }
}

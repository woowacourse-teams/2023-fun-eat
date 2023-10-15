package com.funeat.product.persistence;

import static com.funeat.product.exception.ProductErrorCode.NOT_SUPPORTED_PRODUCT_SORTING_CONDITION;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductSortCondition;
import com.funeat.product.exception.ProductException.NotSupportedProductSortingConditionException;
import java.util.Objects;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private static final String DESC = "desc";

    public static Specification<Product> searchBy(final Category category, final Product lastProduct,
                                                  final ProductSortCondition sortCondition) {
        return (root, query, builder) -> {
            if (DESC.equals(sortCondition.getOrder())) {
                query.orderBy(builder.desc(root.get(sortCondition.getBy())), builder.desc(root.get("id")));
            } else {
                query.orderBy(builder.asc(root.get(sortCondition.getBy())), builder.desc(root.get("id")));
            }

            return Specification
                    .where(sameCategory(category))
                    .and(findNext(lastProduct, sortCondition))
                    .toPredicate(root, query, builder);
        };
    }

    private static Specification<Product> sameCategory(final Category category) {
        return (root, query, builder) -> {
            final Path<Object> categoryPath = root.get("category");

            return builder.equal(categoryPath, category);
        };
    }

    private static Specification<Product> findNext(final Product lastProduct, final ProductSortCondition sortCondition) {
        final String sortBy = sortCondition.getBy();
        final String sortOrder = sortCondition.getOrder();

        return (root, query, builder) -> {
            if (Objects.isNull(lastProduct)) {
                return null;
            }

            final Comparable comparisonValue = (Comparable) getComparisonValue(lastProduct, sortBy);

            return builder.or(
                    sameValue(sortBy, lastProduct.getId(), comparisonValue).toPredicate(root, query, builder),
                    notSameValue(sortBy, sortOrder, comparisonValue).toPredicate(root, query, builder)
            );
        };
    }

    private static Object getComparisonValue(final Product lastProduct, final String sortBy) {
        if ("price".equals(sortBy)) {
            return lastProduct.getPrice();
        }
        if ("averageRating".equals(sortBy)) {
            return lastProduct.getAverageRating();
        }
        if ("reviewCount".equals(sortBy)) {
            return lastProduct.getReviewCount();
        }
        throw new NotSupportedProductSortingConditionException(NOT_SUPPORTED_PRODUCT_SORTING_CONDITION, sortBy);
    }

    private static Specification<Product> sameValue(final String sortBy, final Long lastProductId,
                                                    final Comparable comparisonValue) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get(sortBy), comparisonValue),
                builder.lessThan(root.get("id"), lastProductId));
    }

    private static Specification<Product> notSameValue(final String sortBy, final String sortOrder,
                                                       final Comparable comparisonValue) {
        return (root, query, builder) -> {
            if (DESC.equals(sortOrder)) {
                return builder.lessThan(root.get(sortBy), comparisonValue);
            } else {
                return builder.greaterThan(root.get(sortBy), comparisonValue);
            }
        };
    }
}

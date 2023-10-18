package com.funeat.product.persistence;

import static com.funeat.product.exception.ProductErrorCode.NOT_SUPPORTED_PRODUCT_SORTING_CONDITION;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductSortCondition;
import com.funeat.product.exception.ProductException.NotSupportedProductSortingConditionException;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification() {
    }

    private static final String DESC = "desc";
    private static final String CATEGORY = "category";
    private static final String ID = "id";
    private static final String REVIEW_COUNT = "reviewCount";
    private static final String AVERAGE_RATING = "averageRating";
    private static final String PRICE = "price";

    public static Specification<Product> searchBy(final Category category, final Product lastProduct,
                                                  final ProductSortCondition sortCondition) {
        return (root, query, builder) -> {
            setOrderBy(sortCondition, root, query, builder);

            return Specification
                    .where(sameCategory(category))
                    .and(nextCursor(lastProduct, sortCondition))
                    .toPredicate(root, query, builder);
        };
    }

    private static void setOrderBy(final ProductSortCondition sortCondition, final Root<Product> root,
                                  final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final String sortBy = sortCondition.getBy();
        final String sortOrder = sortCondition.getOrder();

        if (DESC.equals(sortOrder)) {
            query.orderBy(builder.desc(root.get(sortBy)), builder.desc(root.get(ID)));
        } else {
            query.orderBy(builder.asc(root.get(sortBy)), builder.desc(root.get(ID)));
        }
    }

    private static Specification<Product> sameCategory(final Category category) {
        return (root, query, builder) -> {
            final Path<Object> categoryPath = root.get(CATEGORY);

            return builder.equal(categoryPath, category);
        };
    }

    private static Specification<Product> nextCursor(final Product lastProduct, final ProductSortCondition sortCondition) {
        final String sortBy = sortCondition.getBy();
        final String sortOrder = sortCondition.getOrder();

        return (root, query, builder) -> {
            if (Objects.isNull(lastProduct)) {
                return null;
            }

            final Comparable comparisonValue = (Comparable) getComparisonValue(lastProduct, sortBy);

            return builder.or(
                    sameValueAndSmallerId(sortBy, lastProduct.getId(), comparisonValue).toPredicate(root, query, builder),
                    nextValue(sortBy, sortOrder, comparisonValue).toPredicate(root, query, builder)
            );
        };
    }

    private static Object getComparisonValue(final Product lastProduct, final String sortBy) {
        if (PRICE.equals(sortBy)) {
            return lastProduct.getPrice();
        }
        if (AVERAGE_RATING.equals(sortBy)) {
            return lastProduct.getAverageRating();
        }
        if (REVIEW_COUNT.equals(sortBy)) {
            return lastProduct.getReviewCount();
        }
        throw new NotSupportedProductSortingConditionException(NOT_SUPPORTED_PRODUCT_SORTING_CONDITION, sortBy);
    }

    private static Specification<Product> sameValueAndSmallerId(final String sortBy, final Long lastProductId,
                                                                final Comparable comparisonValue) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get(sortBy), comparisonValue),
                builder.lessThan(root.get(ID), lastProductId));
    }

    private static Specification<Product> nextValue(final String sortBy, final String sortOrder,
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

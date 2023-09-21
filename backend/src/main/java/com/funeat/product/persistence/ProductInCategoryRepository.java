package com.funeat.product.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.dto.ProductInCategoryDto;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductInCategoryRepository {

    private final EntityManager entityManager;

    public ProductInCategoryRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProductInCategoryDto> findProductByPriceAscFirst(Category category) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "WHERE p.category = :category "
                        + "ORDER BY p.price asc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByPriceAsc(Category category, Long lastProductId) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "JOIN Product p2 ON p2.id =:lastProductId "
                        + "WHERE p.category = :category AND "
                        + "( "
                        + "  (p.price = p2.price AND p.id<:lastProductId)"
                        + "or p.price >p2.price "
                        + ") "
                        + "ORDER BY p.price asc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("lastProductId", lastProductId)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByPriceDescFirst(Category category) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "WHERE p.category = :category "
                        + "ORDER BY p.price desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByPriceDesc(Category category, Long lastProductId) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "JOIN Product p2 ON p2.id =:lastProductId "
                        + "WHERE p.category = :category AND "
                        + "( "
                        + "  (p.price = p2.price AND p.id<:lastProductId)"
                        + "or p.price < p2.price "
                        + ") "
                        + "ORDER BY p.price desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("lastProductId", lastProductId)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByAverageRatingAscFirst(Category category) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "WHERE p.category = :category "
                        + "ORDER BY p.averageRating asc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByAverageRatingAsc(Category category, Long lastProductId) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "JOIN Product p2 ON p2.id =:lastProductId "
                        + "WHERE p.category = :category AND "
                        + "( "
                        + "  (p.averageRating = p2.averageRating AND p.id<:lastProductId)"
                        + "or p.averageRating > p2.averageRating "
                        + ") "
                        + "ORDER BY p.averageRating asc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("lastProductId", lastProductId)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByAverageRatingDescFirst(Category category) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "WHERE p.category = :category "
                        + "ORDER BY p.averageRating desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByAverageRatingDesc(Category category, Long lastProductId) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "JOIN Product p2 ON p2.id =:lastProductId "
                        + "WHERE p.category = :category AND "
                        + "( "
                        + "  (p.averageRating = p2.averageRating AND p.id<:lastProductId)"
                        + "or p.averageRating < p2.averageRating "
                        + ") "
                        + "ORDER BY p.averageRating desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("lastProductId", lastProductId)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByReviewCountDescFirst(Category category) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "WHERE p.category = :category "
                        + "ORDER BY p.reviewCount desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }

    public List<ProductInCategoryDto> findProductByReviewCountDesc(Category category, Long lastProductId) {
        String jpqlQuery =
                "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, p.reviewCount) "
                        + "FROM Product p "
                        + "JOIN Product p2 ON p2.id =:lastProductId "
                        + "WHERE p.category = :category AND "
                        + "( "
                        + "  (p.reviewCount = p2.reviewCount AND p.id<:lastProductId)"
                        + "or p.reviewCount < p2.reviewCount "
                        + ") "
                        + "ORDER BY p.reviewCount desc, p.id DESC ";

        return entityManager.createQuery(jpqlQuery, ProductInCategoryDto.class)
                .setParameter("lastProductId", lastProductId)
                .setParameter("category", category)
                .setMaxResults(11)
                .getResultList();
    }
}

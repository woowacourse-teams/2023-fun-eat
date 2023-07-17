package com.funeat.review.persistence;

import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Long countByProduct(final Product product);
}

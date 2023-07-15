package com.funeat.review.persistence;

import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByProduct(final Pageable pageable, final Product product);

    List<Review> findReviewsByProductOrderByFavoriteCountDesc(final Pageable pageable, final Product product);
}

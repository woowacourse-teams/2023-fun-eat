package com.funeat.product.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(final Category category, final Pageable pageable);
}

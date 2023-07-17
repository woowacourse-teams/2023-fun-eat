package com.funeat.product.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByType(final CategoryType type);
}

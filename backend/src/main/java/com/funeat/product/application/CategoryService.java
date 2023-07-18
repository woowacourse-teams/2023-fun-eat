package com.funeat.product.application;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.persistence.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategoriesByType(final CategoryType type) {
        return categoryRepository.findAllByType(type);
    }
}

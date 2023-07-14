package com.funeat.product.application;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.persistence.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> findAllCategoriesByType(final CategoryType type) {
        final List<Category> categories = categoryRepository.findAllByType(type);
        return categories.stream()
                .map(CategoryDto::toDto)
                .collect(Collectors.toList());
    }
}

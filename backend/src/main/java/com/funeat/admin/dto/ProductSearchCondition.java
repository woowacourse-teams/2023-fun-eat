package com.funeat.admin.dto;

public class ProductSearchCondition {

    private final String name;
    private final Long id;
    private final Long categoryId;
    private final Long totalElements;
    private final Long prePage;

    public ProductSearchCondition(final String name, final Long id, final Long categoryId,
                                  final Long totalElements, final Long prePage) {
        this.name = name;
        this.id = id;
        this.categoryId = categoryId;
        this.totalElements = totalElements;
        this.prePage = prePage;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Long getPrePage() {
        return prePage;
    }
}

package com.funeat.common.dto;

import org.springframework.data.domain.Page;

public class PageDto {

    private final Long totalDataCount;
    private final Long totalPages;
    private final boolean firstPage;
    private final boolean lastPage;
    private final Long requestPage;
    private final Long requestSize;

    public PageDto(final Long totalDataCount, final Long totalPages, final boolean firstPage, final boolean lastPage,
                   final Long requestPage, final Long requestSize) {
        this.totalDataCount = totalDataCount;
        this.totalPages = totalPages;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.requestPage = requestPage;
        this.requestSize = requestSize;
    }

    public static <T> PageDto toDto(final Page<T> page) {
        return new PageDto(
                page.getTotalElements(),
                Long.valueOf(page.getTotalPages()),
                page.isFirst(),
                page.isLast(),
                Long.valueOf(page.getNumber()),
                Long.valueOf(page.getSize())
        );
    }

    public Long getTotalDataCount() {
        return totalDataCount;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public Long getRequestPage() {
        return requestPage;
    }

    public Long getRequestSize() {
        return requestSize;
    }
}

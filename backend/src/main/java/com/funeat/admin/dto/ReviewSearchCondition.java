package com.funeat.admin.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ReviewSearchCondition {

    private final Long productId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime from;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime to;

    private final Long id;
    private final Long totalElements;
    private final Long prePage;

    public ReviewSearchCondition(final Long productId, final LocalDateTime from, final LocalDateTime to,
                                 final Long id, final Long totalElements, final Long prePage) {
        this.productId = productId;
        this.from = from;
        this.to = to;
        this.id = id;
        this.totalElements = totalElements;
        this.prePage = prePage;
    }

    public Long getProductId() {
        return productId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Long getId() {
        return id;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Long getPrePage() {
        return prePage;
    }
}

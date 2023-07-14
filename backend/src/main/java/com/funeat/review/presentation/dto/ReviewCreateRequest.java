package com.funeat.review.presentation.dto;

import java.util.List;

public class ReviewCreateRequest {

    private Double rating;
    private List<Long> tagIds;
    private String content;
    private Boolean reBuy;
    private Long memberId;

    public ReviewCreateRequest(final Double rating, final List<Long> tagIds,
                               final String content, final Boolean reBuy, final Long memberId) {
        this.rating = rating;
        this.tagIds = tagIds;
        this.content = content;
        this.reBuy = reBuy;
        this.memberId = memberId;
    }

    public Double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Boolean getReBuy() {
        return reBuy;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }
}
//"image": "qwer.png",
//        "rating": 4.5,
//        "tags": [
//        {
//        "id": 5,
//        "name": "단짠단짠"
//        },
//        {
//        "id": 1,
//        "name": "망고망고"
//        }
//        ],
//        "content": "맛있어용~!~!",
//        "rebuy": true

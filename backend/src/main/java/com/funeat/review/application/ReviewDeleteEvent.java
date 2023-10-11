package com.funeat.review.application;

public class ReviewDeleteEvent {

    private String image;

    public ReviewDeleteEvent(final String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}

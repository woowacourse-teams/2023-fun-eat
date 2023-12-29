package com.funeat.banner.dto;

import com.funeat.banner.domain.Banner;

public class BannerResponse {

    private final Long id;
    private final String link;
    private final String image;

    private BannerResponse(final Long id, final String link, final String image) {
        this.id = id;
        this.link = link;
        this.image = image;
    }

    public static BannerResponse toResponse(final Banner banner) {
        return new BannerResponse(banner.getId(), banner.getLink(), banner.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }
}

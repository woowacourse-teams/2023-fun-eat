package com.funeat.banner.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private String image;

    protected Banner() {
    }

    public Banner(final String link, final String image) {
        this.link = link;
        this.image = image;
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

package com.funeat.recipe.domain;

import com.funeat.member.domain.Member;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Recipe {

    private static final double RANKING_GRAVITY = 0.1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long favoriteCount = 0L;

    protected Recipe() {
    }

    public Recipe(final String title, final String content, final Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Recipe(final String title, final String content, final Member member,
                  final Long favoriteCount) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.favoriteCount = favoriteCount;
    }

    public Recipe(final String title, final String content, final Member member, final Long favoriteCount,
                  final LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public Double calculateRankingScore() {
        final long age = ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
        final double denominator = Math.pow(age + 1.0, RANKING_GRAVITY);
        return favoriteCount / denominator;
    }

    public void addFavoriteCount() {
        this.favoriteCount++;
    }

    public void minusFavoriteCount() {
        this.favoriteCount--;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

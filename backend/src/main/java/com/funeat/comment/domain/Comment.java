package com.funeat.comment.domain;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Comment() {
    }

    public Comment(final Recipe recipe, final Member member, final String comment) {
        this.recipe = recipe;
        this.member = member;
        this.comment = comment;
        recipe.getComments().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

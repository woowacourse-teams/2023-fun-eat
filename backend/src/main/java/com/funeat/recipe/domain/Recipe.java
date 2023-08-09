package com.funeat.recipe.domain;

import com.funeat.member.domain.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected Recipe() {
    }

    public Recipe(final String name, final String content, final Member member) {
        this.name = name;
        this.content = content;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
}

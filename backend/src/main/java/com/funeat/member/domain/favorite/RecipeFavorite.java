package com.funeat.member.domain.favorite;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecipeFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Boolean checked;

    protected RecipeFavorite() {
    }

    public RecipeFavorite(final Member member, final Recipe recipe, final Boolean checked) {
        this.member = member;
        this.recipe = recipe;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Boolean getChecked() {
        return checked;
    }
}

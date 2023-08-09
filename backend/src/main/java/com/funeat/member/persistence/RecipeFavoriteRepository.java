package com.funeat.member.persistence;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.recipe.domain.Recipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeFavoriteRepository extends JpaRepository<RecipeFavorite, Long> {

    Optional<RecipeFavorite> findByMemberAndRecipe(final Member member, final Recipe recipe);
}

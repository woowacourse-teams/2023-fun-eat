package com.funeat.recipe.persistence;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findRecipesByMember(final Member member, final Pageable pageable);

    @Query("SELECT r FROM Recipe r "
            + "LEFT JOIN ProductRecipe pr ON pr.recipe.id = r.id "
            + "WHERE pr.product.name LIKE CONCAT('%', :name, '%') "
            + "ORDER BY CASE "
            + "WHEN pr.product.name LIKE CONCAT(:name, '%') THEN 1 "
            + "ELSE 2 END")
    Page<Recipe> findAllByProductNameContaining(@Param("name") final String name, final Pageable pageable);

    Page<Recipe> findAll(final Pageable pageable);

    List<Recipe> findRecipesByOrderByFavoriteCountDesc(final Pageable pageable);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Recipe r WHERE r.id=:id")
    Optional<Recipe> findByIdForUpdate(final Long id);
}

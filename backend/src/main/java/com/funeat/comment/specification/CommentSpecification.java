package com.funeat.comment.specification;

import com.funeat.comment.domain.Comment;
import com.funeat.recipe.domain.Recipe;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {

    private static final List<Class<Long>> COUNT_RESULT_TYPES = List.of(Long.class, long.class);

    public static Specification<Comment> findAllByRecipe(final Recipe recipe, final Long lastCommentId) {
        return (root, query, criteriaBuilder) -> {
            if (!COUNT_RESULT_TYPES.contains(query.getResultType())) {
                root.fetch("member", JoinType.LEFT);
            }

            criteriaBuilder.desc(root.get("id"));

            return Specification
                    .where(lessThan(lastCommentId))
                    .and(equalToRecipe(recipe))
                    .toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Comment> equalToRecipe(final Recipe recipe) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(recipe)) {
                return null;
            }

            final Path<Recipe> recipePath = root.get("recipe");

            return criteriaBuilder.equal(recipePath, recipe);
        };
    }

    private static Specification<Comment> lessThan(final Long commentId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(commentId)) {
                return null;
            }

            final Path<Long> commentIdPath = root.get("id");

            return criteriaBuilder.lessThan(commentIdPath, commentId);
        };
    }
}

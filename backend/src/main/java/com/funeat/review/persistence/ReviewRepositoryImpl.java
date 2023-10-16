package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.TestSortingReviewDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TestSortingReviewDto> getSortingReview(final Member loginMember,
                                                       final Specification<Review> specification,
                                                       final String sortOption) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<TestSortingReviewDto> cq = cb.createQuery(TestSortingReviewDto.class);
        final Root<Review> root = cq.from(Review.class);

        // sortField, sortOrder
        final String[] sortOptionSplit = sortOption.split(",");
        final String sortField = sortOptionSplit[0];
        final String sortOrder = sortOptionSplit[1];

        // join
        final Join<Review, Member> joinMember = root.join("member", JoinType.INNER);

        // left join
        final Join<Review, ReviewFavorite> leftJoinReviewFavorite = root.join("reviewFavorites", JoinType.LEFT);
        final Predicate condition = cb.equal(leftJoinReviewFavorite.get("member"), loginMember);
        leftJoinReviewFavorite.on(condition);

        // select - from - where - order by
        cq.select(getConstruct(root, cb, joinMember, leftJoinReviewFavorite))
                .where(specification.toPredicate(root, cq, cb))
                .orderBy(getOrderBy(root, cb, sortField, sortOrder));

        // limit
        final TypedQuery<TestSortingReviewDto> query = em.createQuery(cq);
        query.setMaxResults(11);

        // result
        return query.getResultList();
    }

    private CompoundSelection<TestSortingReviewDto> getConstruct(final Root<Review> root,
                                                                 final CriteriaBuilder cb,
                                                                 final Join<Review, Member> joinMember,
                                                                 final Join<Review, ReviewFavorite> leftJoinReviewFavorite) {

        return cb.construct(TestSortingReviewDto.class,
                root.get("id"),
                joinMember.get("nickname"),
                joinMember.get("profileImage"),
                root.get("image"),
                root.get("rating"),
                root.get("content"),
                root.get("reBuy"),
                root.get("favoriteCount"),
                leftJoinReviewFavorite.get("favorite"),
                root.get("createdAt"));
    }

    private List<Order> getOrderBy(final Root<Review> root,
                                   final CriteriaBuilder cb,
                                   final String fieldName,
                                   final String sortOption) {
        if ("ASC".equalsIgnoreCase(sortOption)) {
            final Order order = cb.asc(root.get(fieldName));
            return List.of(order, cb.desc(root.get("id")));
        }
        final Order order = cb.desc(root.get(fieldName));
        return List.of(order, cb.desc(root.get("id")));
    }
}

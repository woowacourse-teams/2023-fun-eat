package com.funeat.common.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    public BaseRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Page<T> findAllForPagination(final Specification<T> spec, final Pageable pageable,
                                        final Long totalElements) {
        final TypedQuery<T> query = getQuery(spec, pageable.getSort());

        final int pageSize = pageable.getPageSize();

        if (totalElements == null) {
            return findAll(spec, pageable);
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("페이지는 1미만이 될 수 없습니다.");
        }

        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList(), PageRequest.of(0, pageSize), totalElements);
    }

    @Override
    public List<T> findAllWithSpecification(final Specification<T> spec, final int pageSize) {
        final TypedQuery<T> query = getQuery(spec, Sort.unsorted());
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
}

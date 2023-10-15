package com.funeat.common.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Page<T> findAllForPagination(final Specification<T> spec, final Pageable pageable, final Long totalElements);

    List<T> findAllWithSpecification(final Specification spec, final int size);
}

package com.ll.ilta.domain.problem.repository;

import com.ll.ilta.domain.favorite.entity.Favorite;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Transactional
    void deleteByProblemId(Long problemId);
}

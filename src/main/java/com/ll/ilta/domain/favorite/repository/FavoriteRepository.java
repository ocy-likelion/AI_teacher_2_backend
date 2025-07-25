package com.ll.ilta.domain.favorite.repository;

import com.ll.ilta.domain.favorite.entity.Favorite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

    Optional<Favorite> findByMemberIdAndProblemId(Long id, Long problemId);

    void deleteByProblemId(Long problemId);
}

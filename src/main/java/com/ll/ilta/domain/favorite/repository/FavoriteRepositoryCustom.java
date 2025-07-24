package com.ll.ilta.domain.favorite.repository;

import com.ll.ilta.domain.favorite.dto.FavoriteResponseDto;
import java.util.List;

public interface FavoriteRepositoryCustom {
    List<FavoriteResponseDto> findFavoriteWithCursor(Long memberId, String afterCursor, int limit);
}

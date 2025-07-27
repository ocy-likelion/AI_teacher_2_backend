package com.ll.ilta.domain.favorite.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoriteToggleResponseDto {

    private final boolean isFavorited;

    @Builder
    private FavoriteToggleResponseDto(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public static FavoriteToggleResponseDto of(boolean isFavorited) {
        return FavoriteToggleResponseDto.builder().isFavorited(isFavorited).build();
    }
}

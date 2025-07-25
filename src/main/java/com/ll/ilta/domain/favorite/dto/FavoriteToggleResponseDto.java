package com.ll.ilta.domain.favorite.dto;

import lombok.Getter;

@Getter
public class FavoriteToggleResponseDto {
    private final boolean isFavorited;

    public FavoriteToggleResponseDto(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}

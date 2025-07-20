package com.ll.ilta.domain.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponseDto {

    private final String imageUrl;

    @Builder
    private ImageResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageResponseDto from(String imageUrl) {
        return ImageResponseDto.builder().imageUrl(imageUrl).build();
    }
}

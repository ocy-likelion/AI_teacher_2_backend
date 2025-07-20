package com.ll.ilta.domain.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageUploadResponseDto {

    private final String imageUrl;

    @Builder
    private ImageUploadResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageUploadResponseDto of(String imageUrl) {
        return ImageUploadResponseDto.builder().imageUrl(imageUrl).build();
    }
}

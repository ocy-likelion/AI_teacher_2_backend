package com.ll.ilta.domain.image.dto;

import com.ll.ilta.domain.image.entity.Image;
import com.ll.ilta.domain.problem.entity.Problem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageDto {

    private final Long problemId;
    private final Long memberId;
    private final String imageUrl;

    @Builder
    private ImageDto(Long problemId, Long memberId, String imageUrl) {
        this.problemId = problemId;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
    }

    public static ImageDto of(Problem problem, Image image) {
        return ImageDto.builder().problemId(problem.getId()).memberId(problem.getMember().getId())
            .imageUrl(image.getImageUrl()).build();
    }

}

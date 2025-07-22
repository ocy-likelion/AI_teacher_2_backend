package com.ll.ilta.domain.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupabaseResponseDto {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Id")
    private String id;

    private SupabaseResponseDto(String key, String id) {
        this.key = key;
        this.id = id;
    }
}

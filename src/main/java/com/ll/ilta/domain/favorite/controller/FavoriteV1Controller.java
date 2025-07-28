package com.ll.ilta.domain.favorite.controller;

import com.ll.ilta.domain.favorite.dto.FavoriteResponseDto;
import com.ll.ilta.domain.favorite.dto.FavoriteToggleResponseDto;
import com.ll.ilta.domain.favorite.service.FavoriteService;
import com.ll.ilta.global.common.dto.CursorPaginatedResponseDto;
import com.ll.ilta.global.security.v2.member.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FavoriteV1Controller", description = "즐겨찾기 API")
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
@RestController
public class FavoriteV1Controller {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 토글", description = "문제를 즐겨찾기에 추가하거나 제거")
    @PostMapping
    public ResponseEntity<FavoriteToggleResponseDto> addFavorite(@RequestParam Long problemId,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId(); // 로그인된 사용자 ID
        FavoriteToggleResponseDto response = favoriteService.toggleFavorite(memberId, problemId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "즐겨찾기 목록 커서 기반 페이징을 지원")
    @GetMapping("/list")
    public ResponseEntity<CursorPaginatedResponseDto<FavoriteResponseDto>> getFavoriteList(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(name = "after_cursor", required = false) String afterCursor
    ) {
        Long memberId = principalDetails.getMemberId();
        CursorPaginatedResponseDto<FavoriteResponseDto> response = favoriteService.getFavoriteList(memberId, limit,
            afterCursor);
        return ResponseEntity.ok(response);
    }
}

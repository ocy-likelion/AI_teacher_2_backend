package com.ll.ilta.domain.favorite.service;

import com.ll.ilta.domain.favorite.dto.FavoriteResponseDto;
import com.ll.ilta.domain.favorite.dto.FavoriteToggleResponseDto;
import com.ll.ilta.domain.favorite.entity.Favorite;
import com.ll.ilta.domain.favorite.repository.FavoriteRepository;
import com.ll.ilta.domain.member.v2.entity.Member;
import com.ll.ilta.domain.member.v2.repository.MemberRepository;
import com.ll.ilta.domain.problem.entity.Problem;
import com.ll.ilta.domain.problem.repository.ProblemRepository;
import com.ll.ilta.global.common.dto.CursorPaginatedResponseDto;
import com.ll.ilta.global.common.service.CursorUtil;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import com.ll.ilta.global.payload.exception.handler.ProblemHandler;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private static final String FAVORITE_LIST_URL = "/api/v1/favorite/list";

    private final FavoriteRepository favoriteRepository;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CursorPaginatedResponseDto<FavoriteResponseDto> getFavoriteList(Long memberId, int limit,
        String afterCursor) {
        List<FavoriteResponseDto> favorites = favoriteRepository.findFavoriteWithCursor(memberId, afterCursor,
            limit + 1);

        if (favorites.isEmpty()) {
            return CursorPaginatedResponseDto.of(favorites, limit, false, null, buildSelfUrl(limit, afterCursor), null);
        }

        boolean hasNextPage = favorites.size() > limit;

        String nextCursor = null;
        if (hasNextPage) {
            FavoriteResponseDto lastItem = favorites.get(limit - 1); // 현재 페이지 마지막
            nextCursor = CursorUtil.encodeCursor(lastItem.getId(), lastItem.getCreatedAt());
        }

        if (hasNextPage) {
            favorites = favorites.subList(0, limit);
        }

        String selfUrl = buildSelfUrl(limit, afterCursor);
        String nextUrl = hasNextPage ? buildNextUrl(limit, nextCursor) : null;

        return CursorPaginatedResponseDto.of(favorites, limit, hasNextPage, nextCursor, selfUrl, nextUrl);
    }

    @Transactional
    public FavoriteToggleResponseDto toggleFavorite(Long memberId, Long problemId) {
        Optional<Favorite> existing = favoriteRepository.findByMemberIdAndProblemId(memberId, problemId);

        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return new FavoriteToggleResponseDto(false);
        } else {
            Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemHandler(ErrorStatus.NOT_FOUND_PROBLEM));
            Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthHandler(ErrorStatus.NOT_FOUND_USER));

            favoriteRepository.save(Favorite.of(problem, member));
            return new FavoriteToggleResponseDto(true);
        }

    }

    private String buildSelfUrl(int limit, String afterCursor) {
        StringBuilder url = new StringBuilder(FAVORITE_LIST_URL + "?limit=").append(limit);
        if (afterCursor != null) {
            url.append("&after_cursor=").append(afterCursor);
        }
        return url.toString();
    }

    private String buildNextUrl(int limit, String nextCursor) {
        return FAVORITE_LIST_URL + "?limit=" + limit + "&after_cursor=" + nextCursor;
    }
}

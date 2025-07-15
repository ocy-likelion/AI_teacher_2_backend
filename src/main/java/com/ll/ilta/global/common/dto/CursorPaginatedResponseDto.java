package com.ll.ilta.global.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPaginatedResponseDto<T> {

    private List<T> problems;
    private Pagination pagination;
    private Links links;

    public static <T> CursorPaginatedResponseDto<T> of(List<T> problems, int limit,
        boolean hasNextPage, String nextCursor,
        String selfUrl, String nextUrl) {
        Pagination pagination = new Pagination(limit, hasNextPage, nextCursor);
        Links links = new Links(selfUrl, nextUrl);
        return new CursorPaginatedResponseDto<>(problems, pagination, links);
    }

    @Getter
    @AllArgsConstructor
    public static class Pagination {

        private int limit;
        private boolean hasNextPage;
        private String nextCursor;
    }

    @Getter
    @AllArgsConstructor
    public static class Links {

        private String self;
        private String next;
    }
}

package com.ll.ilta.global.common.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class CursorUtil {

    public record Cursor(Long id, LocalDateTime createdAt) {
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String encodeCursor(Long id, LocalDateTime createdAt) {
        String raw = id + "|" + createdAt.format(FORMATTER);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public static Cursor decodeCursor(String afterCursor) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(afterCursor);
        String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] parts = decoded.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid cursor format");
        }
        Long id = Long.parseLong(parts[0]);
        LocalDateTime createdAt = LocalDateTime.parse(parts[1], FORMATTER);
        return new Cursor(id, createdAt);
    }
}

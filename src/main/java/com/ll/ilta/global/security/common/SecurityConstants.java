package com.ll.ilta.global.security.common;

import java.util.Arrays;
import java.util.stream.Stream;

public class SecurityConstants {

    public static final String[] swaggerUrls = {
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**"
    };

    public static final String[] allowUrls = {
        "/api/v2/posts/**",
        "/login",
        "/auth/login/kakao/**",
        "/api/v2/oauth",
        "/api/v2/members/logout"
    };

    // 허용 Urls (swagger + allowUrls)
    public static String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(allowUrls))
        .toArray(String[]::new);

}

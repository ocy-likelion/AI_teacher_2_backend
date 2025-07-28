package com.ll.ilta.global.security.constant;

import java.util.Arrays;
import java.util.stream.Stream;

public class SecurityConstants {

    public static final String[] swaggerUrls = {"/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**"};
    public static final String[] allowUrls = {
        "/api/v2/posts/**",
        "/api/v2/replies/**",
        "/login",
        "/auth/login/kakao/**"
    };

    // 허용 Urls
    public static String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(allowUrls))
        .toArray(String[]::new);

}

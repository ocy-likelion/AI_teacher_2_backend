package com.ll.ilta.global.security.v2.jwt;

import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenValidityMilliseconds;

    public JwtUtil(
        @Value("${jwt.secret}") final String secretKey,
        @Value("${jwt.expiration}") final long accessTokenValidityMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMilliseconds = accessTokenValidityMilliseconds;
    }


    // HTTP 요청의 'Authorization' 헤더에서 JWT 액세스 토큰을 검색
    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("[JWT] No Token found in Authorization header");
            throw new AuthHandler(ErrorStatus.TOKEN_NOT_FOUND);
        }
        log.info("[JWT] Authorization 헤더에서 추출된 토큰 앞 10자리: {}",
            authorization.substring(7, Math.min(17, authorization.length())));
        return authorization.split(" ")[1];
    }

    public String createAccessToken(String email, String role) {
        return createToken(email, role, accessTokenValidityMilliseconds);
    }

    private String createToken(String email, String role, long validityMilliseconds) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("role", role);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(validityMilliseconds / 1000);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(tokenValidity.toInstant()))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getEmail(String token) {

        //return getClaims(token).getBody().get("email", String.class);
        String email = getClaims(token).getBody().get("email", String.class);
        log.info("[JWT] 토큰에서 추출한 이메일: {}", email);
        return email;
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            Date expiredDate = claims.getBody().getExpiration();
            String email = claims.getBody().get("email", String.class);
            log.info("[JWT] 토큰 유효함 ✅ | 이메일: {}, 만료시간: {}", email, expiredDate);
            //Date now = new Date();
            return expiredDate.after(new Date()); //now
        } catch (ExpiredJwtException e) {
            log.warn("[JWT] 만료된 토큰 ❌ | 앞 10자리: {} | 에러: {}", token.substring(0, Math.min(10, token.length())), e.getMessage());
            log.info("[*] _AUTH_EXPIRE_TOKEN");

            throw new AuthHandler(ErrorStatus.AUTH_EXPIRE_TOKEN);
        } catch (SignatureException
                 | SecurityException
                 | IllegalArgumentException
                 | MalformedJwtException
                 | UnsupportedJwtException e) {
            log.warn("[JWT] 유효하지 않은 토큰 ❌ | 앞 10자리: {} | 에러: {}", token.substring(0, Math.min(10, token.length())), e.getMessage());
            log.info("[*] AUTH_INVALID_TOKEN");
            throw new AuthHandler(ErrorStatus.AUTH_INVALID_TOKEN);
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }

}

package com.ll.ilta.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.ilta.domain.member.v2.dto.KakaoDTO;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class KakaoUtil {

    @Value("${kakao.auth.client}")
    private String client;
    @Value("${kakao.auth.redirect}")
    private String redirect;
    @Value("${kakao.auth.clientSecret}")
    private String clientSecret;

    @PostConstruct
    public void init() {
        log.info("Kakao clientId = {}", client);
        log.info("Kakao redirectUri = {}", redirect);
        log.info("Kakao clientSecret = {}", clientSecret);
    }

    public KakaoDTO.OAuthToken requestToken(String accessCode) {
        log.info("requestToken called with code={}", accessCode);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", client);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirect);
            params.add("code", accessCode);

            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest, String.class);
            log.info("Kakao token response status: {}", response.getStatusCode());
            log.info("Kakao token response body: {}", response.getBody());

            ObjectMapper objectMapper = new ObjectMapper();

            KakaoDTO.OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), KakaoDTO.OAuthToken.class);
            log.info("oAuthToken : {}", oAuthToken.getAccess_token());
            return oAuthToken;

        } catch (
            JsonProcessingException e) {
            log.error("Json processing error: ", e);
            throw new RuntimeException("Failed to parse Kakao token response", e);
        } catch (
            Exception e) {
            log.error("Exception in requestToken: ", e);
            throw new RuntimeException("Failed to request Kakao token", e);
        }
    }


    public KakaoDTO.KakaoProfile requestMemberProfile(KakaoDTO.OAuthToken oAuthToken) {
        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<Void> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate2.exchange(
            "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, kakaoProfileRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDTO.KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoDTO.KakaoProfile.class);
        } catch (JsonProcessingException e) {
            log.info(Arrays.toString(e.getStackTrace()));
            throw new AuthHandler(ErrorStatus.PARSING_ERROR);
        }

        return kakaoProfile;
    }
}

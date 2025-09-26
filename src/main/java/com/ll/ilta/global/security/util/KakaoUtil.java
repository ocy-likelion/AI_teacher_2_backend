package com.ll.ilta.global.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.ilta.domain.member.dto.KakaoDTO;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class KakaoUtil {

    private final RestTemplate restTemplate;

    @Value("${kakao.auth.client}")
    private String client;
    @Value("${kakao.auth.redirect}")
    private String redirect;
    @Value("${kakao.auth.clientSecret}")
    private String clientSecret;

    public KakaoUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public KakaoDTO.OAuthToken requestToken(String accessCode) {
        log.info("requestToken called with code={}", accessCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirect);
        params.add("code", accessCode);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
            );

            log.info("Kakao token response status: {}", response.getStatusCode());
            log.info("Kakao token response body: {}", response.getBody());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), KakaoDTO.OAuthToken.class);

        } catch (JsonProcessingException e) {
            log.error("Json processing error: ", e);
            throw new AuthHandler(ErrorStatus.KAKAO_PARSING_ERROR);
        } catch (Exception e) {
            log.error("Exception in requestToken: ", e);
            throw new AuthHandler(ErrorStatus.KAKAO_REQUEST_ERROR);
        }
    }

    public KakaoDTO.KakaoProfile requestMemberProfile(KakaoDTO.OAuthToken oAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oAuthToken.getAccess_token());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), KakaoDTO.KakaoProfile.class);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse Kakao profile response", e);
            throw new AuthHandler(ErrorStatus.KAKAO_PARSING_ERROR);
        } catch (Exception e) {
            log.error("Exception in requestMemberProfile: ", e);
            throw new AuthHandler(ErrorStatus.KAKAO_REQUEST_ERROR);
        }
    }
}

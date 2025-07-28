package com.ll.ilta.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.ilta.domain.member.v2.dto.KakaoDTO;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.AuthHandler;
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

    @Value("${spring.kakao.auth.client}")
    private String client;
    @Value("${spring.kakao.auth.redirect}")
    private String redirect;

    public KakaoDTO.OAuthToken requestToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("redirect_url", redirect);
        params.add("code", accessCode);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
            kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDTO.OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoDTO.OAuthToken.class);
            log.info("oAuthToken : " + oAuthToken.getAccess_token());
        } catch (JsonProcessingException e) {
            throw new AuthHandler(ErrorStatus.PARSING_ERROR);
        }
        return oAuthToken;
    }
}

//    public KakaoDTO.KakaoProfile requestMemberProfile(KakaoDTO.OAuthToken oAuthToken){
//        RestTemplate restTemplate2 = new RestTemplate();
//        HttpHeaders headers2 = new HttpHeaders();
//
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//        headers2.add("Authorization","Bearer "+ oAuthToken.getAccess_token());
//
//        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity <>(headers2);
//
//        ResponseEntity<String> response2 = restTemplate2.exchange(
//            "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, kakaoProfileRequest, String.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        KakaoDTO.KakaoProfile kakaoProfile = null;
//
//        try {
//            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoDTO.KakaoProfile.class);
//        } catch (JsonProcessingException e) {
//            log.info(Arrays.toString(e.getStackTrace()));
//            throw new AuthHandler(ErrorStatus._PARSING_ERROR);
//        }
//
//        return kakaoProfile;
//    }

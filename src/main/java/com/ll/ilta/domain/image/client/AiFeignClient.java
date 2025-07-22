package com.ll.ilta.domain.image.client;

import com.ll.ilta.domain.image.dto.AiResponseDto;
import com.ll.ilta.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@FeignClient(name = "aiFeignClient", url = "${ai.service.url}", configuration = FeignConfig.class)
public interface AiFeignClient {

    @PostMapping(value = "/math-explain", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    AiResponseDto sendImageToAiServer(@RequestPart("image") MultipartFile file);
}

package com.ll.ilta.domain.problem.client;

import com.ll.ilta.domain.problem.dto.MathExplainRequestDto;
import com.ll.ilta.domain.problem.dto.MathExplainResponseDto;
import com.ll.ilta.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@FeignClient(name = "mathExplainClient", url = "${ai.service.url}", configuration = FeignConfig.class)
public interface MathExplainClient {

    @PostMapping("/math-explain")
    MathExplainResponseDto sendImageUrlToPythonServer(@RequestBody MathExplainRequestDto request);

    @PostMapping(value = "/math-explain", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    MathExplainResponseDto sendImageToPythonServer(@RequestPart("image") MultipartFile file);
}

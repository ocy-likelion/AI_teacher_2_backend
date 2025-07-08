package com.ll.ilta.domain.child.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/child", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ChildController", description = "Child 관련 API")
@RequiredArgsConstructor
public class ApiV1ChildController {

    @PostMapping
    @Operation(
        summary = "자녀 생성 (Swagger 응답 테스트용)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "자녀 생성 완료",
                content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "success": true,
                              "message": "자녀 등록 완료"
                            }
                            """
                    )
                )
            )
        }
    )
    public ResponseEntity<String> createChild() {
        // 실제 로직 없이, 고정된 JSON 문자열 반환
        String response = """
                {
                  "success": true,
                  "message": "자녀 등록 완료"
                }
            """;
        return ResponseEntity.ok(response);
    }
}

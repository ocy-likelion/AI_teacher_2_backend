package com.ll.ilta.global.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.ilta.global.payload.response.BaseResponse;
import com.ll.ilta.global.payload.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException)
        throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(403);

        BaseResponse<Object> errorResponse =
            BaseResponse.onFailure(
                ErrorStatus.FORBIDDEN.getCode(),
                ErrorStatus.FORBIDDEN.getMessage(),
                null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

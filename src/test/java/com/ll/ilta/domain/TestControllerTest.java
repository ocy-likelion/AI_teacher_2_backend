package com.ll.ilta.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.ilta.global.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestController.class) // 테스트 대상 컨트롤러 지정
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_success() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.result").value("성공!"));
    }

    @Test
    public void test_failed() throws Exception {
        mockMvc.perform(get("/failed"))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.isSuccess").value(false));
    }
}

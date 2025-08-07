package com.ll.ilta.global;

import com.ll.ilta.global.payload.code.status.ErrorStatus;
import com.ll.ilta.global.payload.exception.handler.TestHandler;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void failedTest() {
        if (1 == 1) {
            throw new TestHandler(ErrorStatus.BAD_REQUEST);
        }
    }
}

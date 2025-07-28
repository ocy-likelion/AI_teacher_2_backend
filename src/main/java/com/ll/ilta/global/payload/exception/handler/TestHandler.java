package com.ll.ilta.global.payload.exception.handler;

import com.ll.ilta.global.payload.code.BaseErrorCode;
import com.ll.ilta.global.payload.exception.GeneralException;

public class TestHandler extends GeneralException {
    public TestHandler(BaseErrorCode code) {
        super(code);
    }
}

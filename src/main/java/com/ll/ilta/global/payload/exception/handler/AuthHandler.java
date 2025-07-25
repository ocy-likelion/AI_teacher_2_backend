package com.ll.ilta.global.payload.exception.handler;

import com.ll.ilta.global.payload.code.BaseErrorCode;
import com.ll.ilta.global.payload.exception.GeneralException;

public class AuthHandler extends GeneralException {

    public AuthHandler(BaseErrorCode code) {
        super(code);
    }
}

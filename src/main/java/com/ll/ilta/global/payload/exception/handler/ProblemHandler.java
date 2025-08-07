package com.ll.ilta.global.payload.exception.handler;

import com.ll.ilta.global.payload.code.BaseErrorCode;
import com.ll.ilta.global.payload.exception.GeneralException;

public class ProblemHandler extends GeneralException  {
    public ProblemHandler(BaseErrorCode code) {
        super(code);
    }
}

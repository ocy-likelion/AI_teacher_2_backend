package com.ll.ilta.global.payload.exception.handler;

import com.ll.ilta.global.payload.code.BaseErrorCode;
import com.ll.ilta.global.payload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(BaseErrorCode code) {
        super(code);
    }

}

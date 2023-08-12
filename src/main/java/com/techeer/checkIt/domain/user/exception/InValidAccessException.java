package com.techeer.checkIt.domain.user.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class InValidAccessException extends BusinessException {
    public InValidAccessException() {
        super(ErrorCode.ACCESS_INVALID_VALUE);
    }
}

package com.techeer.checkIt.domain.user.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class InValidPasswordException extends BusinessException {
    public InValidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}

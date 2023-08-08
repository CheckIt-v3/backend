package com.techeer.checkIt.domain.user.exception;

import com.techeer.checkIt.global.error.exception.BusinessException;

import static com.techeer.checkIt.global.error.ErrorCode.UNAUTHORIZED_ACCESS_ERROR;

public class UnAuthorizedAccessException extends BusinessException {
    public UnAuthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS_ERROR);
    }
}

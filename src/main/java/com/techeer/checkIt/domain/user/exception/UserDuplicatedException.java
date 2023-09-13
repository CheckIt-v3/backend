package com.techeer.checkIt.domain.user.exception;

import com.techeer.checkIt.global.error.exception.BusinessException;

import static com.techeer.checkIt.global.error.ErrorCode.USER_USERNAME_DUPLICATED;

public class UserDuplicatedException extends BusinessException {
    public UserDuplicatedException() {
        super(USER_USERNAME_DUPLICATED);
    }
}

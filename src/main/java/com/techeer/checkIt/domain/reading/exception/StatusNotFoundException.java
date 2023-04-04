package com.techeer.checkIt.domain.reading.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class StatusNotFoundException extends BusinessException {
    public StatusNotFoundException() {super(ErrorCode.READING_STATUS_NOT_FOUND_ERROR);}
}

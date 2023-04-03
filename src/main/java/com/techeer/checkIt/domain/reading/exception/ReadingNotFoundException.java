package com.techeer.checkIt.domain.reading.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class ReadingNotFoundException extends BusinessException {
    public ReadingNotFoundException() {super(ErrorCode.READING_NOT_FOUND_ERROR);}
}

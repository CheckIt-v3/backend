package com.techeer.checkIt.domain.reading.exception;

import com.techeer.checkIt.global.error.exception.BusinessException;

import static com.techeer.checkIt.global.error.ErrorCode.READING_DUPLICATED_ERROR;

public class ReadingDuplicatedException extends BusinessException {
    public ReadingDuplicatedException() {
        super(READING_DUPLICATED_ERROR);
    }
}

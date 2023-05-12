package com.techeer.checkIt.domain.reading.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class PageValidationException extends BusinessException {
    public PageValidationException(int num) {super(ErrorCode.PAGE_VALIDATION_NEGATIVE_VALUE);}
    public PageValidationException(boolean flag) {super(ErrorCode.PAGE_VALIDATION_OUT_OF_PAGE);}
}

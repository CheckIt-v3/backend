package com.techeer.checkIt.domain.book.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class BookNotFoundException extends BusinessException{
    public BookNotFoundException() {
        super(ErrorCode.BOOK_NOT_FOUND_ERROR);
    }
}


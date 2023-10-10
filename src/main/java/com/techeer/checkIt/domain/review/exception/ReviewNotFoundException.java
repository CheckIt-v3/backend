package com.techeer.checkIt.domain.review.exception;

import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class ReviewNotFoundException extends BusinessException {
  public ReviewNotFoundException() {
    super(ErrorCode.REVIEW_NOT_FOUND_ERROR);
  }
}

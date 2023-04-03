package com.techeer.checkIt.domain.readingVolume.exception;

import com.techeer.checkIt.domain.reading.exception.ReadingNotFoundException;
import com.techeer.checkIt.global.error.ErrorCode;
import com.techeer.checkIt.global.error.exception.BusinessException;

public class ReadingVolumeNotFoundException extends BusinessException {
    public ReadingVolumeNotFoundException() {super(ErrorCode.READING_VOLUME_NOT_FOUND_ERROR);}
}

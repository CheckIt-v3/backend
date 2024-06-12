package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;

import java.util.List;

public interface ReadingRepositoryCustom {
    List<BookReadingRes> findReadingsByUserIdAndStatus(Long userId, ReadingStatus status);
}

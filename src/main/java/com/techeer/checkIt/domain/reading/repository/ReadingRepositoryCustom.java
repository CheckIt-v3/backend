package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ReadingRepositoryCustom {
    Slice<BookReadingRes> findReadingsByUserIdAndStatus(Long userId, ReadingStatus status, Pageable pageable);
}

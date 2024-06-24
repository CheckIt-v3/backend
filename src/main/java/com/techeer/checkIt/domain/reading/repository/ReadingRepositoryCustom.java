package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReadingRepositoryCustom {
    Page<BookReadingRes> findReadingsByUserIdAndStatus(Long userId, ReadingStatus status, Pageable pageable);
}

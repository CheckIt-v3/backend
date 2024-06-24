package com.techeer.checkIt.domain.reading.dto.response;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingRes {
    private Slice<BookReadingRes> bookInfos;
    private ReadingStatus status;
}

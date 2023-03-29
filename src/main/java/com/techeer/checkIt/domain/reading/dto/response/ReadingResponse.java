package com.techeer.checkIt.domain.reading.dto.response;

import com.techeer.checkIt.domain.book.dto.Response.BookResponse;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingResponse {
    private BookResponse book;
    private ReadingStatus status;
}

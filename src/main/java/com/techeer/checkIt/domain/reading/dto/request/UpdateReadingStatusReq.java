package com.techeer.checkIt.domain.reading.dto.request;

import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReadingStatusReq {
    private Long bookId;
    private int lastPage;
    private ReadingStatus status;
}

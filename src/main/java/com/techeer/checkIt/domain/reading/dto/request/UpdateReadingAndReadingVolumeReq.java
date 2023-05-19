package com.techeer.checkIt.domain.reading.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReadingAndReadingVolumeReq {
    private Long bookId;
    private int lastPage;

}

package com.techeer.checkIt.domain.reading.dto.response;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReadingAndReadingVolumeRes {
    private int lastPage;
    private int pages;
}


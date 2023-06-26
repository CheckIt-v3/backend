package com.techeer.checkIt.domain.reading.dto.response;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateReadingAndReadingVolumeRes {
    private int lastPage;
    private int pages;
}


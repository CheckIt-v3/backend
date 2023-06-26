package com.techeer.checkIt.domain.reading.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateLastPageAndPercentageRes {
    private int lastPage;
    private double percentage;
}

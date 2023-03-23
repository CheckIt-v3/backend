package com.techeer.checkIt.domain.reading.dto.request;

import lombok.*;

public class CreateReadingRequest {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateReading{
        private Long bookId;
        private int lastPage;
        private String status;
    }
}

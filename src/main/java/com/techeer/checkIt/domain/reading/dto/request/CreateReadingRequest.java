package com.techeer.checkIt.domain.reading.dto.request;

import lombok.*;
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateReadingRequest {
        private Long bookId;
        private int lastPage;
        private String status;
}

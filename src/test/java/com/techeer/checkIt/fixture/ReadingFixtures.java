package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;

public class ReadingFixtures {
    public static final CreateReadingReq TEST_READING =
            CreateReadingReq.builder()
                    .bookId(1L)
                    .lastPage(30)
                    .status("READING")
                    .build();
}


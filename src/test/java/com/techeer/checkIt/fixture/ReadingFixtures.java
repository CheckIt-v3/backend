package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

public class ReadingFixtures {
    public static final CreateReadingReq TEST_READINGREQ =
            CreateReadingReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status("READING")
                    .build();
    public static final Reading TEST_READING =
            Reading.builder()
                    .user(TEST_USER)
                    .book(TEST_BOOKENT)
                    .status(ReadingStatus.READING)
                    .lastPage(81)
                    .build();
}


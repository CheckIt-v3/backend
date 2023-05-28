package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.entity.Reading;
import static com.techeer.checkIt.fixture.UserFixtures.*;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.*;
import static com.techeer.checkIt.fixture.BookFixtures.*;

public class ReadingFixtures {
    public static final CreateReadingReq TEST_READING_REQ =
            CreateReadingReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status("READING")
                    .build();
    public static final CreateReadingReq TEST_READING_REQ2 =
            CreateReadingReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status("READ")
                    .build();
    public static final UpdateReadingStatusReq TEST_UPDATE_READ_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(736)
                    .status(READ)
                    .build();
    public static final UpdateReadingStatusReq TEST_UPDATE_READING_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(130)
                    .status(READING)
                    .build();
    public static final UpdateReadingStatusReq TEST_UPDATE_UNREAD_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status(UNREAD)
                    .build();

    public static final Reading TEST_READING =
            Reading.builder()
                    .user(TEST_USER)
                    .book(TEST_BOOK_ENT)
                    .lastPage(81)
                    .build();
    public static final Reading TEST_READING2 =
            Reading.builder()
                    .user(TEST_USER)
                    .book(TEST_BOOK_ENT)
                    .lastPage(81)
                    .build();
}


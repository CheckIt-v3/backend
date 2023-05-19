package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.reading.dto.response.UpdateReadingAndReadingVolumeRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;

import java.time.LocalDate;

import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

public class ReadingVolumeFixtures {
    public static final double TEST_READINGVOLUME =
            10.0;
    public static final double TEST_READINGVOLUME2 =
            15.0;

    public static final ReadingVolume TEST_READINGVOLUME_ENT =
            ReadingVolume.entityBuilder()
                    .id(1L)
                    .user(TEST_USER)
                    .todayPages(10)
                    .date(LocalDate.now())
                    .build();

    public static final UpdateReadingAndReadingVolumeReq TEST_READINGVOLUME_UPDATE_REQ =
            UpdateReadingAndReadingVolumeReq.builder()
                    .bookId(1L)
                    .lastPage(82)
                    .build();

    public static final UpdateReadingAndReadingVolumeRes TEST_READINGVOLUME_UPDATE_RES =
            UpdateReadingAndReadingVolumeRes.builder()
                    .lastPage(82)
                    .pages(10)
                    .build();
}

package com.techeer.checkIt.domain.reading.dto.response;

import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeReq;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReadingAndReadingVolumeRes {
    private int lastPage;
    private int pages;

    public static UpdateReadingAndReadingVolumeRes of(UpdateReadingAndReadingVolumeReq body, ReadingVolume readingVolume) {
        return UpdateReadingAndReadingVolumeRes.builder()
                .lastPage(body.getLastPage())
                .pages(readingVolume.getTodayPages())
                .build();
    }
}


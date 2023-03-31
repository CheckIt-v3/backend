package com.techeer.checkIt.domain.reading.dto.response;

import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingAndReadingVolumeRequestDto;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateReadingAndReadingVolumeResponseDto {
    private int lastPage;
    private int pages;

    public static UpdateReadingAndReadingVolumeResponseDto of(UpdateReadingAndReadingVolumeRequestDto body, ReadingVolume readingVolume) {
        return UpdateReadingAndReadingVolumeResponseDto.builder()
                .lastPage(body.getLastPage())
                .pages(readingVolume.getTodayPages())
                .build();
    }
}


package com.techeer.checkIt.domain.readingVolume.dto.response;


import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchReadingVolumesRes {
    private LocalDate date;
    private int page;
}

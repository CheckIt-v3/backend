package com.techeer.checkIt.domain.book.dto.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookRes {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String coverImageUrl;
    private int height;
    private int width;
    private int pages;
    private int likes;
    private boolean likeStatus;
}

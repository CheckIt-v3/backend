package com.techeer.checkIt.domain.book.dto.Response;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookResponse {
    private String title;
    private String author;
    private String publisher;
    private String coverImageUrl;
    private int height;
    private int width;
    private int thickness;
}

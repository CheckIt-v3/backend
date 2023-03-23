package com.techeer.checkIt.domain.book.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private String title;
    private String author;
    private String publisher;
    private String coverImageUrl;
}

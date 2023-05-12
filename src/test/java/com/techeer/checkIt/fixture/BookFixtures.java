package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;

public class BookFixtures {
    public static final BookRes TEST_BOOK =
            BookRes.builder()
                    .author("작가")
                    .title("재미있는 책")
                    .coverImageUrl("imageurl.com")
                    .height(300)
                    .width(200)
                    .thickness(20)
                    .publisher("일이삼 출판사")
                    .build();
}

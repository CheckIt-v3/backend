package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;

public class BookFixtures {
    public static final BookRes TEST_BOOK =
            BookRes.builder()
                    .author("게리 켈러, 제이 파파산")
                    .title("원씽")
                    .coverImageUrl("https://image.yes24.com/goods/9349031/XL")
                    .height(280)
                    .width(210)
                    .thickness(20)
                    .publisher("비즈니스북스")
                    .build();
    public static final BookRes TEST_BOOK2 =
            BookRes.builder()
                    .author("김호연")
                    .title("불편한 편의점")
                    .coverImageUrl("https://image.yes24.com/goods/117030338/L'")
                    .height(280)
                    .width(135)
                    .thickness(20)
                    .publisher("비즈니스북스")
                    .build();
    public static final Book BOOK_ENT =
            Book.builder()
                    .author("게리 켈러, 제이 파파산")
                    .title("원씽")
                    .coverImageUrl("https://image.yes24.com/goods/9349031/XL")
                    .height(280)
                    .width(210)
                    .thickness(20)
                    .publisher("비즈니스북스")
                    .pages(148)
                    .category("자기계발")
                    .build();
    public static final Book BOOK_ENT2 =
            Book.builder()
                    .author("김호연")
                    .title("불편한 편의점")
                    .coverImageUrl("https://image.yes24.com/goods/117030338/L'")
                    .height(280)
                    .width(135)
                    .thickness(20)
                    .publisher("비즈니스북스")
                    .pages(268)
                    .category("자기계발")
                    .build();
}

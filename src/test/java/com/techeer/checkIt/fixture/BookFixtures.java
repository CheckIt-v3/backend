package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;

public class BookFixtures {
    public static final BookRes TEST_BOOK =
            BookRes.builder()
                    .author("세이노")
                    .title("세이노의 가르침")
                    .coverImageUrl("http://image.yes24.com/goods/117014613/XL")
                    .height(224)
                    .width(153)
                    .pages(400)
                    .publisher("데이원")
                    .build();
    public static final BookRes TEST_BOOK2 =
            BookRes.builder()
                    .author("김미경")
                    .title("김미경의 마흔 수업")
                    .coverImageUrl("https://image.yes24.com/goods/117188090/XL")
                    .height(210)
                    .width(145)
                    .pages(170)
                    .publisher("어웨이크북스")
                    .build();
    public static final Book TEST_BOOK_ENT =
            Book.builder()
                    .author("세이노")
                    .title("세이노의 가르침")
                    .coverImageUrl("https://image.yes24.com/goods/117014613/XL")
                    .height(224)
                    .width(153)
                    .thickness(40)
                    .publisher("데이원")
                    .pages(736)
                    .category("국내도서 > 자기계발 > 처세술/삶의 자세\n" +
                            "국내도서 > 자기계발 > 성공학/경력관리")
                    .build();
    public static final Book TEST_BOOK_ENT2 =
            Book.builder()
                    .author("김호연")
                    .title("불편한 편의점")
                    .coverImageUrl("https://image.yes24.com/goods/117030338/L'")
                    .height(210)
                    .width(145)
                    .thickness(17)
                    .publisher("어웨이크북스")
                    .pages(268)
                    .category("국내도서 > 자기계발 > 처세술/삶의 자세")
                    .build();
}

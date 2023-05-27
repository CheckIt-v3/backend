package com.techeer.checkIt.fixture;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.entity.Book;

public class BookFixtures {
    public static final BookRes TEST_BOOK =
            BookRes.builder()
// <<<<<<< BE/chore/#49
                    .id(1L)
                    .author("세이노")
                    .title("재미있는 책")
                    .coverImageUrl("http://image.yes24.com/goods/117014613/XL")
                    .height(224)
                    .width(736)
                    .thickness(40)
                    .publisher("데이원")
                    .build();
    public static final Book TEST_BOOKENT =
            Book.builder()
                    .author("세이노")
                    .title("재미있는 책")
                    .coverImageUrl("http://image.yes24.com/goods/117014613/XL")
                    .height(224)
                    .width(153)
                    .thickness(40)
                    .publisher("데이원")
                    .pages(736)
                    .category("  국내도서 > 자기계발 > 처세술/삶의 자세\n" +
                            "  국내도서 > 자기계발 > 성공학/경력관리")
                    .build();

    public static final Book TEST_BOOKENT2 =
            Book.builder()
                    .author("김미경")
                    .title("김미경의 마흔 수업")
                    .coverImageUrl("http://image.yes24.com/goods/117188090/XL")
                    .height(210)
                    .width(145)
                    .thickness(17)
                    .publisher("어웨이크북스")
                    .pages(300)
                    .category("   국내도서 > 자기계발 > 처세술/삶의 자세")
// =======
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
// >>>>>>> develop
                    .build();
}

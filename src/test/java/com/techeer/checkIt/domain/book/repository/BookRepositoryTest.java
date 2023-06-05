//package com.techeer.checkIt.domain.book.repository;
//
//import com.techeer.checkIt.domain.book.entity.Book;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import java.util.List;
//
//import static com.techeer.checkIt.fixture.BookFixtures.*;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class BookRepositoryTest {
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @Test
//    @DisplayName("Repository) 책 검색")
//    void findByTitle() {
//
//        // given
//        Book book = bookRepository.save(TEST_BOOK_ENT);  // 세이노
//        Book book2 = bookRepository.save(TEST_BOOK_ENT); // 불편한 편의점
//        String keyword = "세이노";
//
//        // when
//        List<Book> books = bookRepository.findByTitle(keyword);
//
//        // then
//        assertThat(books.get(0).getTitle()).as("책검색 결과 %s, 검색한 책 : %s",books.get(0).getTitle(), book2.getTitle()).isEqualTo(book.getTitle());
//    }
//}
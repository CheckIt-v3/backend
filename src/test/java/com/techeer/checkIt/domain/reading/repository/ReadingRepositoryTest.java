package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.repository.BookRepository;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class  ReadingRepositoryTest {
    @Autowired
    private ReadingRepository readingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    private User user = TEST_USER;
    private Book book = TEST_BOOKENT;


    @Test
    @DisplayName("user와 book으로 원하는 reading을 불러올 수 있는지 확인한다.")
    void findByUserAndBook() {
        userRepository.save(user);
        bookRepository.save(book);

        Reading reading = Reading.builder()
                .user(user)
                .book(book)
                .lastPage(30)
                .status(ReadingStatus.READING)
                .build();
        readingRepository.save(reading);
        Reading test = readingRepository.findByUserAndBook(user,book).orElseThrow(null);
        Assertions.assertThat(reading).isEqualTo(test);
  }
}

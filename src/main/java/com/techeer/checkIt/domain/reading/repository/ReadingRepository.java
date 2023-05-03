package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading,Long> {
    List<Reading> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ReadingStatus status);
    Optional<Reading> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, ReadingStatus status);
    Optional<Reading> findLastPageByUserAndBook(User user, Book book);
}

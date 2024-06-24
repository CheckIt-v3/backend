package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading,Long>, ReadingRepositoryCustom {
    @EntityGraph(attributePaths = {"book"})
    @Query("SELECT r FROM Reading r WHERE r.user.id = :userId AND r.status = :status")
    Page<Reading> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ReadingStatus status, Pageable pageable);
    Optional<Reading> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, ReadingStatus status);
    Optional<Reading> findByUserAndBook(User user, Book book);
    boolean existsByUserAndBook(User user, Book book);
}

package com.techeer.checkIt.domain.book.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book,Long> {
    @Query("select b from Book b where b.id = :id AND b.isDeleted = false")
    Optional<Book> findByBookId(@Param("id") Long id);

    @Query("select b from Book b where b.id IN :bookId")
    Slice<Book> findByBookIdIn(@Param("bookId") List<Long> bookId, Pageable pageable);
}

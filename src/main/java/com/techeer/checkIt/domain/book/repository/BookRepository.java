package com.techeer.checkIt.domain.book.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select b from Book b where b.isDeleted is false and b.title like %:title%")
    List<Book> findByTitle(@Param("title") String title);

    @Query("select b from Book b where b.id = :id AND b.isDeleted = false")
    Optional<Book> findByBookId(@Param("id") Long id);
}

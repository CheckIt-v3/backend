package com.techeer.checkIt.domain.book.repository;

import com.techeer.checkIt.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

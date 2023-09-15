package com.techeer.checkIt.domain.book.repository;

import com.techeer.checkIt.domain.book.entity.BookDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {
    List<BookDocument> findByTitleContaining(String title);
    Page<BookDocument> findAll(Pageable pageable);
}

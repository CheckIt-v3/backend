package com.techeer.checkIt.domain.book.repository;

import com.techeer.checkIt.domain.book.entity.BookDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {
    List<BookDocument> findByTitleContaining(String title);
}

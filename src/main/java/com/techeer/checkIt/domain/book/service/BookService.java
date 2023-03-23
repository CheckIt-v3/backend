package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book findById(Long bid) {
        return bookRepository.findById(bid).orElseThrow(null); // 오류코드를 아직 작성하지 않았으니 일단은 null
    }
}

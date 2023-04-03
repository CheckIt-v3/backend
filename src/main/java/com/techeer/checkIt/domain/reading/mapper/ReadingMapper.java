package com.techeer.checkIt.domain.reading.mapper;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReadingMapper {
    public Reading toEntity(User user, Book book, int lastPage, ReadingStatus status) {
        return Reading.builder()
                .user(user)
                .book(book)
                .lastPage(lastPage)
                .status(status)
                .build();
    }
}

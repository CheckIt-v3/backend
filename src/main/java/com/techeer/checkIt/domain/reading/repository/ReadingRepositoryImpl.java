package com.techeer.checkIt.domain.reading.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.book.dto.Response.QBookReadingRes;
import com.techeer.checkIt.domain.book.entity.QBook;
import com.techeer.checkIt.domain.reading.entity.QReading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import com.techeer.checkIt.domain.review.entity.QReview;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReadingRepositoryImpl implements ReadingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookReadingRes> findReadingsByUserIdAndStatus(Long userId, ReadingStatus status) {
        QReading qReading = QReading.reading;
        QBook qBook = QBook.book;
        QReview qReview = QReview.review;

        return queryFactory
                .select(new QBookReadingRes(
                        qBook,
                        qReview.grade.coalesce(0)))
                .from(qReading)
                .leftJoin(qReading.book, qBook)
                .leftJoin(qBook.reviews, qReview)
                .where(qReading.user.id.eq(userId)
                        .and(qReading.status.eq(status)))
                .fetch();
    }
}

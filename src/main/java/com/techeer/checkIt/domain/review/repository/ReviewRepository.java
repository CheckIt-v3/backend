package com.techeer.checkIt.domain.review.repository;

import com.techeer.checkIt.domain.review.entity.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review,Long> {
  @Query("select r from Review r where r.user.nickname LIKE :userName AND r.book.id = :bookId AND r.isDeleted = false")
  Optional<Review> findByUserNameBookId(@Param("userName") String userName, @Param("bookId") Long bookId);
}
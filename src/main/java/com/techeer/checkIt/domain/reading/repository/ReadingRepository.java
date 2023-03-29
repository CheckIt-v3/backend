package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading,Long> {
        List<Reading> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ReadingStatus status);
}

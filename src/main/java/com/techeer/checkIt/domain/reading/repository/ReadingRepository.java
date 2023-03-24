package com.techeer.checkIt.domain.reading.repository;

import com.techeer.checkIt.domain.reading.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading,Long> {
}

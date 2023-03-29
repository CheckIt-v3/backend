package com.techeer.checkIt.domain.readingVolume.repository;


import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReadingVolumeRepository extends JpaRepository<ReadingVolume,Long> {
    Optional<ReadingVolume> findReadingVolumeByUserAndDate(User user, LocalDate date);
    boolean existsByUserAndDate(User user, LocalDate date);
}

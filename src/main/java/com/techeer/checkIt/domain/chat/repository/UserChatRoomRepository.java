package com.techeer.checkIt.domain.chat.repository;

import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    boolean existsByUserId(Long userId);
    List<UserChatRoom> findByUserId(Long userId);
}

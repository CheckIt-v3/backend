package com.techeer.checkIt.domain.chat.repository;

import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    List<ChatRoom> findByUserId(Long userId);
}
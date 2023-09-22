package com.techeer.checkIt.domain.chat.repository;

import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}

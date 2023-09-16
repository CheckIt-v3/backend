package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "채팅 API")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
@Slf4j
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    @MessageMapping("/chat/rooms/{roomId}/message")
    public void chat(@DestinationVariable Long roomId, CreateMessageReq messageRequest) {
        sendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageRequest.getContent());
        log.info("Message [{}] send by member: {} to chatting room: {}", messageRequest.getContent(), messageRequest.getSender(), roomId);
    }
}

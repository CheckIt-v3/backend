package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Api(tags = "채팅 API")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Controller
@Slf4j
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final UserChatRoomService userChatRoomService;


    @MessageMapping("/chat/rooms/{chatRoomId}/message")
    public void chat(@DestinationVariable Long chatRoomId, CreateMessageReq messageRequest) {
        sendingOperations.convertAndSend("/sub/chat/rooms/" + chatRoomId, messageRequest.getContent());
        userChatRoomService.save(chatRoomId, messageRequest);
        log.info("Message [{}] send by member: {} to chatting room: {}", messageRequest.getContent(), messageRequest.getUserId(), chatRoomId);
    }
}

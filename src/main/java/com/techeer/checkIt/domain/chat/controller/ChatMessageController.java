package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final UserChatRoomService userChatRoomService;


    @MessageMapping("/chat/rooms/{chatRoomId}/message")
    public void chat(StompHeaderAccessor headerAccessor,
                     @DestinationVariable Long chatRoomId,
                     @Valid CreateMessageReq messageRequest) {
        UserDetail userDetail = (UserDetail) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userDetail");

        sendingOperations.convertAndSend("/sub/chat/rooms/" + chatRoomId, messageRequest.getContent());
        userChatRoomService.saveMessage(userDetail.getUser(), chatRoomId, messageRequest);
        log.info("Message [{}] send by user: {} to chatting room: {}", messageRequest.getContent(), userDetail.getUsername(), chatRoomId);
    }
}

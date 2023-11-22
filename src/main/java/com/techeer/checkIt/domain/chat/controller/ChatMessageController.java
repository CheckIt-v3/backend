package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

import static com.techeer.checkIt.global.constant.RabbitMQ.*;

@Controller
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageController {
    private final UserChatRoomService userChatRoomService;
    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("chat.message.{chatRoomId}")
    public void chat(StompHeaderAccessor headerAccessor,
                     @DestinationVariable Long chatRoomId,
                     @Payload CreateMessageReq messageRequest) {
        UserDetail userDetail = (UserDetail) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userDetail");

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, messageRequest);
        
        userChatRoomService.saveMessage(userDetail.getUser(), chatRoomId, messageRequest);
        log.info("Message [{}] send by user: {} to chatting room: {}", messageRequest.getContent(), messageRequest.getSender(), chatRoomId);
    }

    // 메세지가 큐에 도착할 때 실행
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(CreateMessageReq messageRequest) {
        log.info("message.getText = {}", messageRequest.getContent());
    }
}

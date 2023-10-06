package com.techeer.checkIt.global.stomp;

import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.domain.user.service.CustomUserDetailService;
import com.techeer.checkIt.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserChatRoomService userChatRoomService;
    private final CustomUserDetailService customUserDetailService;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String authorization = jwtTokenProvider.extractJwt(accessor);
//            final String destination = accessor.getDestination();
//            if(destination == null ) {throw new NullPointerException("없음");}
//            log.info("destination: {}", destination);

            // 세션에 사용자 정보 저장
            if (jwtTokenProvider.validateToken(authorization)) {
                String username = jwtTokenProvider.extractUsername(authorization);
                UserDetail userDetail = (UserDetail) customUserDetailService.loadUserByUsername(username);

//                // 채팅방 입장
//                if (!userChatRoomService.duplicatedUserChatRoom(userDetail.getUser())) {    // 유저 채팅방 중복 예외처리
//                    ChatRoom chatRoom = userChatRoomService.createChatRoom();
//                    userChatRoomService.createUserChatRoom(userDetail.getUser(), chatRoom.getId());
//                    log.info("SUBSCRIBED {}, {}", userDetail.getUserId(), chatRoom.getId());
//                }

                Objects.requireNonNull(accessor.getSessionAttributes()).put("userDetail", userDetail);
            }
        }
        return message;
    }
}

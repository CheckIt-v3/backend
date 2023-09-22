package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("api/v1/chat/rooms")
public class ChatRoomController {

    private final UserChatRoomService userChatRoomService;

    // 채팅하기 버튼 누르면 채팅방 생성
    @PostMapping
    public ResponseEntity<ResultResponse> createChatRoom (@AuthenticationPrincipal UserDetail userDetail) {

        ChatRoom chatRoom = userChatRoomService.createChatRoom();
        UserChatRoom userChatRoom = userChatRoomService.createUserChatRoom(userDetail.getUser(), chatRoom.getId());
        // TODO: 유저 채팅방 중복 예외처리 해야함
//        if (userChatRoomService.duplicatedUserChatRoom(userChatRoom)) {
//            throw new ChatRoomDuplicatedException();
//        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CHATROOM_CREATE_SUCCESS, userChatRoom));
    }
}

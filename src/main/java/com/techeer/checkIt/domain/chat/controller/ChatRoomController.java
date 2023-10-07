package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.response.ChatMessageRes;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
import com.techeer.checkIt.domain.chat.entity.UserChatRoom;
import com.techeer.checkIt.domain.chat.exception.ChatRoomDuplicatedException;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "채팅 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("api/v1/chat/rooms")
public class ChatRoomController {

    private final UserChatRoomService userChatRoomService;

    @ApiOperation(value = "채팅방 생성 API")
    @PostMapping
    public ResponseEntity<ResultResponse> createChatRoom (@AuthenticationPrincipal UserDetail userDetail) {
        // TODO: 유저 채팅방 중복 예외처리 다시 해야함
        if (userChatRoomService.duplicatedUserChatRoom(userDetail.getUser())) {
            throw new ChatRoomDuplicatedException();
        }
        ChatRoom chatRoom = userChatRoomService.createChatRoom();
        UserChatRoom userChatRoom = userChatRoomService.createUserChatRoom(userDetail.getUser(), chatRoom.getId());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CHATROOM_CREATE_SUCCESS, userChatRoom));
    }

    @ApiOperation(value = "채팅 내역 조회 API")
    @GetMapping
    public ResponseEntity<List<ChatMessageRes>> getChatMessage (@AuthenticationPrincipal UserDetail userDetail, Long chatRoomId) {
        List<ChatMessageRes> chatMessageList = userChatRoomService.findChatMessage(userDetail.getUser(), chatRoomId);
        return ResponseEntity.ok(chatMessageList);
    }
}

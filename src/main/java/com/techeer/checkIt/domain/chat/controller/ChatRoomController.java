package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.response.ChatMessageRes;
import com.techeer.checkIt.domain.chat.dto.response.ChatRoomRes;
import com.techeer.checkIt.domain.chat.entity.ChatRoom;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "채팅 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("api/v1/chat/rooms")
public class ChatRoomController {
    private final UserChatRoomService userChatRoomService;

    @ApiOperation(value = "채팅방 생성(사용자만) API")
    @PostMapping
    public ResponseEntity<ResultResponse> createChatRoom (@AuthenticationPrincipal UserDetail userDetail) {
        // 사용자는 관리자와의 채팅방 1개만 존재한다.
        if (userChatRoomService.duplicatedUserChatRoom(userDetail.getUser())) { // 사용자가 이미 채팅방 입장했으면
            throw new ChatRoomDuplicatedException();
        }
        ChatRoom chatRoom = userChatRoomService.createChatRoom();
        userChatRoomService.createUserChatRoom(userDetail.getUser(), chatRoom.getId());

        return ResponseEntity.ok(ResultResponse.of(ResultCode.CHATROOM_CREATE_SUCCESS, chatRoom.getId()));
       // TODO: 관리자가 먼저 채팅하는 경우는 없다는 가정하에 (더 나은 로직 생기면 수정하기)
    }

    @ApiOperation(value = "채팅 내역 조회 API")
    @GetMapping("{chatRoomId}/messages")
    public ResponseEntity<ResultResponse> getChatMessage (@PathVariable Long chatRoomId) {
        // TODO: 해당하는 유저만 조회할 수 있도록 추가 작성하기
        List<ChatMessageRes> chatMessageList = userChatRoomService.findChatMessage(chatRoomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CHAT_SUCCESS, chatMessageList));
    }

    @ApiOperation(value = "채팅방 목록 조회(관리자만) API")
    @GetMapping("/list")
    public ResponseEntity<ResultResponse> getChatRoomList () {
        List<ChatRoomRes> chatRoomList = userChatRoomService.findChatRoom();

        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CHATROOM_SUCCESS, chatRoomList));
    }
}

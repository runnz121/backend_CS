package malangcute.bellytime.bellytimeCustomer.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomDeleteRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomInviteFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomShopListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.CreateRoomRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;


    @MessageMapping(value = "/chat/chatting")
    public void chatController(MessageDto messageDto) {
        chatService.saveLog(messageDto);
        template.convertAndSend("/sub/chatting/room/" + messageDto.getRoomId(), MessageDto.send(messageDto));
    }


    // 방생성 api -> roomId 반환 (이미 존재하면 있는 roomid 반환)
    @PostMapping("/chat/create")
    public ResponseEntity<?> createRoom(@RequireLogin User user, @RequestBody CreateRoomRequest createRoomRequest) {
        RoomIdResponse response =  chatService.checkExistsRoomId(user, createRoomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //내가 갖고 있는 방 목록 조회 -> 친구
    @GetMapping("/chat/list/friend")
    public ResponseEntity<?> getMyFriendRoomList(@RequireLogin User user) {
        List<ChatRoomFriendListResponse> friendList = chatService.friendChatRoomList(user);
        return ResponseEntity.ok(friendList);
    }

    //채팅방 -> 가게
    @GetMapping("/chat/list/shop")
    public ResponseEntity<?> getMyShopRoomList(@RequireLogin User user) {
        List<ChatRoomShopListResponse> shopList = chatService.shopChatRoomList(user);
        return ResponseEntity.ok(shopList);
    }

    //채팅방 삭제
    @PostMapping("/chat/exit")
    public void deleteRoom(@RequireLogin User user, @RequestBody ChatRoomDeleteRequest request) {
        MessageDto messageDto = chatService.deleteRoomService(user, request.getChatRoomId());
        chatService.saveLog(messageDto);
        template.convertAndSend("/sub/chatting/room/" + messageDto.getRoomId(), messageDto);
    }

    //채팅 로그 갖고오기
    @PostMapping("/chat/chatlog")
    public ResponseEntity<List<MessageDto>> chatLog(@RequestBody RoomIdRequest request) {
        List<MessageDto> list = chatService.getChatLog(request);
        return ResponseEntity.ok(list);
    }

    //친구 초대하기
    @PostMapping("/chat/add/friend")
    public void addFriend(@RequireLogin User user, @RequestBody ChatRoomInviteFriendsRequest request) {
        chatService.addFriend(user, request);
        List<MessageDto> messageDtos = chatService.sendInvitedMessage(request);
        for (MessageDto messageDto : messageDtos) {
            template.convertAndSend("/sub/chatting/room/" + messageDto.getRoomId(), messageDto );
        }
    }
}

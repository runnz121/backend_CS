package malangcute.bellytime.bellytimeCustomer.chat.controller;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    private final ChatService chatService;


    //heatlh check
//    @MessageMapping("/")
//    @SendTo("/topic/roomId")
//    public Message boradCast(Message message){
//        return message;
//    }


    //채팅 컨트롤러
    @MessageMapping("/chat/chatting") //클라이언트에서 수신되는 곳
    public void chatController(MessageDto messageDto) {
        System.out.println(messageDto.getContent());
        template.convertAndSend("/sub/chatting/room" + messageDto.getRoomId(), messageDto); // 클이언트로 전송
    }


    // 방생성 api -> roomId 반환
    @PostMapping("/chat/create")
    public ResponseEntity<?> createRoom(@RequireLogin User user, @RequestBody CreateRoomRequest createRoomRequest) {
        RoomIdResponse response =  chatService.createRoomService(user, createRoomRequest);
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
    @DeleteMapping("/chat/delete")
    public ResponseEntity<?> deleteRoom(@RequireLogin User user, @RequestBody ChatRoomDeleteRequest request) {
        chatService.deleteRoomService(user, request.getRoomId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제완료");
    }

}

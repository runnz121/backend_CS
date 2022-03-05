package malangcute.bellytime.bellytimeCustomer.chat.controller;


import com.amazonaws.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import net.minidev.json.JSONArray;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate template;


//        //채팅 컨트롤러 (stomp)
    @MessageMapping(value = "/chat/chatting") //클라이언트에서 수신되는 곳
    public void chatController(MessageDto messageDto) {
        //로그를 저장
        chatService.saveLog(messageDto);
        template.convertAndSend("/sub/chatting/room/" + messageDto.getRoomId(), MessageDto.send(messageDto)); // 클이언트로 전송
    }

    // 방생성 api -> roomId 반환
    @PostMapping("/chat/create")
    public ResponseEntity<?> createRoom(@RequireLogin User user, @RequestBody CreateRoomRequest createRoomRequest) {
        RoomIdResponse response =  chatService.createRoomService(user, createRoomRequest);
        System.out.println("incontroller");
        System.out.println(user.getEmail());
        System.out.println(createRoomRequest.getInviteId());
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
    @DeleteMapping("/chat/exit")
    public ResponseEntity deleteRoom(@RequireLogin User user, @RequestBody ChatRoomDeleteRequest request) {
        chatService.deleteRoomService(user, request.getChatRoomId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제완료");
    }


    //채팅 로그 갖고오기
    @PostMapping("/chat/chatlog")
    public ResponseEntity<List<MessageDto>> chatLog(@RequestBody RoomIdRequest request) {
        List<MessageDto> list = chatService.getChatLog(request);
        return ResponseEntity.ok(list);
    }

    //친구 초대하기
    @PostMapping("/chat/add/friend")
    public ResponseEntity addFriend(@RequireLogin User user, @RequestBody ChatRoomFriendAddRequest request) {
        chatService.addFriend(user, request);
        return ResponseEntity.ok().body("초대완료");
    }

}

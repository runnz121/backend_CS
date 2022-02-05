package malangcute.bellytime.bellytimeCustomer.chat.controller;


import com.amazonaws.Response;
import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.config.KafkaConstants;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Message;
import malangcute.bellytime.bellytimeCustomer.chat.dto.CreateRoomWithRequest;
import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
//@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    private final ChatService chatService;

    //@PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    @MessageMapping("/chat")
    public void sendMessage(@RequireLogin User user, @RequestBody Message message) {
        message.setTime(LocalDateTime.now().toString());
        try {
            chatService.sendMsg(user, message);

            //카프카 토픽에 메시지 보내는 부분
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    // 방 생성
    @PostMapping("/chat/room")
    public ResponseEntity<?> createRoom(@RequireLogin User user, @RequestBody CreateRoomWithRequest request) {
        chatService.createRoomService(user, request);
        return
    }


    // websocket
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //토픽 구독자 모두에게 메시지를 보냄
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor accessor) {
        //웹소켓세션에 새로운 유저 등록
        accessor.getSessionAttributes().put("username", message.getAuthor());
        return message;
    }
}

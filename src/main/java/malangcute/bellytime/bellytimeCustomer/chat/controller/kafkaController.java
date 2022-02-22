//package malangcute.bellytime.bellytimeCustomer.chat.controller;
//
//
//import lombok.AllArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.chat.config.KafKaConstants;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
//import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
//import malangcute.bellytime.bellytimeCustomer.user.domain.User;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.ExecutionException;
//
//@RestController
//@AllArgsConstructor
//public class kafkaController {
//
//
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//
//    private final ChatService chatService;
//
//
//    //클라이언트에서 온 메시지 수신
//    @PostMapping(value = "/message", consumes = "application/json", produces = "application/json")
//    //@MessageMapping("/message") -> socketjs 전용연결
//    public void sendMessage( @RequestBody MessageDto message) throws ExecutionException, InterruptedException {
//
//        System.out.println(message.getSender());
//        System.out.println(message.getRoomId());
//        System.out.println(message.getContent());
//
//        String topicName = message.getRoomId();
//
//        kafkaTemplate.send(topicName, message).get();
//      //  kafkaTemplate.send(KafKaConstants.KAFKA_TOPIC, message).get();
//
//   }
//}

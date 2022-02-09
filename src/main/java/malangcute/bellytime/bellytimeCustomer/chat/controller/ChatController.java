//package malangcute.bellytime.bellytimeCustomer.chat.controller;
//
//
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.chat.domain.KafkaTopicConstants;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
//import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
//import malangcute.bellytime.bellytimeCustomer.chat.service.Receiver;
//import malangcute.bellytime.bellytimeCustomer.chat.service.Sender;
//import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
//import malangcute.bellytime.bellytimeCustomer.user.domain.User;
//import org.apache.kafka.common.message.ConsumerProtocolAssignment;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
////@RequestMapping("/chat")
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final Sender sender;
//
//    private final ChatService chatService;
//
//    private final Receiver receiver;
//
//    private final KafkaTopicConstants kafkaTopicConstants;
//
////    private static String BOOT_TOPIC = "sample";
////
////    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
////    //@MessageMapping("/message")//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
////    //@SendTo("/topic/public")
////    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
////    public void sendMessage(@RequestBody  MessageDto message) throws Exception {
////        //Thread.sleep(1000); // simulated delay
////        message.setMessage(message.getMessage());
////        sender.send(BOOT_TOPIC, message);
////    }
//
//    //roomId(topic) 이름 기반으로 라우팅 -> 보내기버튼
//    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
//   // @MessageMapping("/app") -> 리엑트의 Sokcet js와 연동시
//    public void sendMessageWith(@RequestBody MessageDto message) throws Exception {
//
//        message.setMessage(message.getMessage());
//        sender.send(message.getRoomName(), message);
//    }
//
//
//
////    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
////    // @MessageMapping("/app") -> 리엑트의 Sokcet js와 연동시
////    public void sendMessageWith( @RequestParam("roomId") String roomId, @RequestBody MessageDto message) {
////
////        InfoSenderDto.of(roomId, message.getGroupId());
////        message.setMessage(message.getMessage());
////        sender.send(roomId, message);
////    }
//
////    @PostMapping("/api/check")
////    public void setListener(@RequestBody InfoSenderDto infoSenderDto) {
////        kafkaTopicConstants.Constants(infoSenderDto.getRoomId());
////    }
//
//
//
//    // 방 생성-> 방번호 반환-> 알람서비스로 상대편에게도 보내주기
//    @PostMapping("/chat/room")
//    public ResponseEntity<?> createRoom(@RequireLogin User user, @RequestBody CreateRoomWithRequest request) {
//        GetRoomAndGroupDto roomInfo = chatService.checkRoomExist(user, request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(roomInfo);
//    }
//
//
//
//
//
//
//
//
//
//
//    //@PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
////    @MessageMapping(value = "/chat/enter")
////    public void message(MessageDto messageDto) {
////        messageDto.setMessage(messageDto.getWriter());
////        simpMessagingTemplate.convertAndSend("/sub/chat/room" + messageDto.getRoomName(), messageDto);
////    }
//
//
//
//
//
//
//
//
//
//
//
//
//    /**
//     * rabbitmq controller
//     */
//
////    private final RabbitTemplate template;
////
////    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
////    private final static String CHAT_QUEUE_NAME = "chat.queue";
////
////    @MessageMapping("chat.enter.{chatRoomId}")
////    public void enter(MessageDto chat, @DestinationVariable String chatRoomId){// @DestinationVariable은 Restfult API에서 @PathVariable과 비슷한 용도이다.
////
////        chat.setMessage("입장하셨습니다.");
////
////        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat); // exchange /// convertAndSend([exchange 이름], routing-key, 전송하고자 하는 것)
////
////        //template.convertAndSend("room." + chatRoomId, chat); //queue
////        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
////    }
////
////    @MessageMapping("chat.message.{chatRoomId}")
////    public void send(MessageDto chat, @DestinationVariable String chatRoomId){
////
////
////        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
////        //template.convertAndSend( "room." + chatRoomId, chat);
////        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
////    }
////
////    //receive()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그용도)
////    @RabbitListener(queues = CHAT_QUEUE_NAME) //@RabbitListener(queues = CHAT_QUEUE_NAME) 어노테이션은, 'chat.queue'라는 Queue을 구독해 그 큐에 들어온 메세지를 소비하는 "소비자"가 되겠다는 어노테이션이다
////    public void receive(MessageDto chat){
////
////        System.out.println("received : " + chat.getMessage());
////    }
//
//
//
//}

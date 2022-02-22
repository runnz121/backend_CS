//package malangcute.bellytime.bellytimeCustomer.chat.controller;
//
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
//import org.springframework.amqp.core.MessageDeliveryMode;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//public class RabbitMqController {
//
//    private final SimpMessagingTemplate template;
//
//    private final ChatService chatService;
//
//    private final RabbitTemplate rabbitTemplate;
//
//
//    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
//    private final static String CHAT_QUEUE_NAME = "chat.queue";
//    private final static String CHAT_TOPIC = "amq.topic";
////
////    private final ConnectionFactory connectionFactory;
//
//
//    //heatlh check
////    @MessageMapping("/")
////    @SendTo("/topic/roomId")
////    public Message boradCast(Message message){
////        return message;
////    }
//
//
//    //채팅 컨트롤러 (stomp)
////    @MessageMapping(value = "/chat/chatting") //클라이언트에서 수신되는 곳
////    public void chatController(MessageDto messageDto) {
////        System.out.println(messageDto.getContent());
////        System.out.println(messageDto.getRoomId());
////        template.convertAndSend("/sub/chatting/room/" + messageDto.getRoomId(), messageDto); // 클이언트로 전송
////    }
//
//
//
//    //rabbitmq controller
//    @MessageMapping("chat.message.{roomId}")
//    public void send(MessageDto messageDto, @DestinationVariable String roomId) {
//        System.out.println(roomId);
//        System.out.println("message"+messageDto.getRoomId());
//        System.out.println(messageDto.getContent());
//
//        //rabbitTemplate.convertAndSend(MessageProperties.PERSISTENT_TEXT_PLAIN);
//        rabbitTemplate.convertAndSend(CHAT_TOPIC, "room." + roomId, messageDto, message -> {
//            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
//            return message;
//        });
//    }
//
//    /**
//     * 메세지 영속화를 위한 컨트롤러
//     */
////
////    Connection connection = null;
////    Channel channel = null;
////
////    @MessageMapping("chat.message.{roomId}")
////    public void send(MessageDto messageDto, @DestinationVariable String roomId) throws IOException, TimeoutException {
////
////        System.out.println(messageDto.getContent());
////        ConnectionFactory factory = new ConnectionFactory();
////        connection = factory.newConnection();
////        channel = connection.createChannel();
////
////        channel.queueDeclare("room."+roomId, true, false, false, null);
////
////        System.out.println("roomid" + roomId);
////        byte[] data = messageDto.getContent().getBytes();
////        System.out.println(data.toString());
////        channel.basicPublish(CHAT_EXCHANGE_NAME, "room."+roomId, MessageProperties.PERSISTENT_TEXT_PLAIN, data);
////    }
//
//
//
//
////    //rabbitmq 리스너 -> 큐에 들어온걸 소비 하겠다
////    @RabbitListener(queues = CHAT_QUEUE_NAME)
////    public void receive(MessageDto messageDto) throws IOException {
//////
//////        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//////            String message = new String(delivery.getBody(), "UTF-8");
//////            System.out.println(" [x] Received '" + message + "'");
//////        };
//////        channel.basicConsume(CHAT_EXCHANGE_NAME, true, deliverCallback, consumerTag -> { });
////
////        System.out.println("received : " + messageDto.getContent());
////    }
//
//
//}

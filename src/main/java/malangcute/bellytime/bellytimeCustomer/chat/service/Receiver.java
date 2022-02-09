//package malangcute.bellytime.bellytimeCustomer.chat.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.chat.domain.KafkaTopicConstants;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.InfoSenderDto;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import net.minidev.json.JSONUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//
//@Service
//@RequiredArgsConstructor
//public class Receiver {
//
//    private final SimpMessagingTemplate template;
//
//    @Autowired
//    private final KafkaTopicConstants kafkaTopicConstants;
//
//
//    //@KafkaListener(id="main-listener", topics = "${topic.boot}")
////    @KafkaListener(groupId = "main-listener", topics = "sample")
//
//    //@KafkaListener(topics = "#{${kafkaTopicConstants.Constants()}}" , groupId = "main")
//    public void receive(MessageDto message) throws Exception {
//        //String[] message = consumerRecord.value().toString().split("\\\\u0001");
//        //String message = consumerRecord.value().toString();
//
//
//
//        HashMap<String, String> msg = new HashMap<>();
//        msg.put("message",message.getMessage());
//        msg.put("author",message.getWriter());
//
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(msg);
//
//        //topic/gorup 부분이 APP.js의 topics = {} 부분과 일치해야함
//        System.out.println("@@@@@@@@@@@@@" + json);
//        this.template.convertAndSend("/topic/group", json);
//    }
//}
//package malangcute.bellytime.bellytimeCustomer.chat.service;
//
//import lombok.AllArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.chat.controller.ChatController;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import org.apache.logging.log4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class Sender {
//
//    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
//
//
//    public void send(String topic, MessageDto data) {
//
//        System.out.println("in sender : " + topic);
//        kafkaTemplate.send(topic, data);
//    }
//}
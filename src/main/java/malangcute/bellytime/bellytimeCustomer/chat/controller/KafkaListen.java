//package malangcute.bellytime.bellytimeCustomer.chat.controller;
//
//import lombok.AllArgsConstructor;
//import malangcute.bellytime.bellytimeCustomer.chat.config.KafKaConstants;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.PartitionOffset;
//import org.springframework.kafka.annotation.TopicPartition;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@AllArgsConstructor
//@Component
//public class KafkaListen {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//
////
//    @KafkaListener(
//            topics = KafKaConstants.KAFKA_TOPIC,
//            topicPartitions = {
//                    @TopicPartition(topic = KafKaConstants.KAFKA_TOPIC, partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
//            },
//            groupId = KafKaConstants.GROUP_ID
//    )
//    public void listen(MessageDto message) {
//
//        System.out.println("sending via kafka");
//        System.out.println("listner" + message.getRoomId());
//        simpMessagingTemplate.convertAndSend("/topic/group/"+message.getRoomId(), message);
//    }
//
//
//    public static void consume() {
//
//        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(){
//            //토픽리스트를 매개변수로 준다. 구독신청을 한다.
//            consumer.subscribe(Arrays.asList("test-topic"));
//            while (true) {
//                //컨슈머는 토픽에 계속 폴링하고 있어야한다. 아니면 브로커는 컨슈머가 죽었다고 판단하고, 해당 파티션을 다른 컨슈머에게 넘긴다.
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records)
//                    log.info("Topic: {}, Partition: {}, Offset: {}, Key: {}, Value: {}\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
//            }
//        } finally {}
//    }
//
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        consume();
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
//
//
//
//    //기본형
////
////    @KafkaListener(
////            topics = KafKaConstants.KAFKA_TOPIC,
////            groupId = KafKaConstants.GROUP_ID
////    )
////    public void listen(MessageDto message) {
////
////        System.out.println("sending via kafka");
////        System.out.println("listner" + message.getRoomId());
////        simpMessagingTemplate.convertAndSend("/topic/group/"+message.getRoomId(), message);
////    }
////
//
//    //메세지 처음부터 갖고오기
//
////
////    @KafkaListener(id = "sampler",
////            topicPartitions =
////                    {@TopicPartition(topic = KafKaConstants.KAFKA_TOPIC,
////                            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")),
////                            @TopicPartition(topic = KafKaConstants.KAFKA_TOPIC, partitions = {"0"})})
////    public void receiveMessage(String message) {
////        try {
////            JSONObject incomingJsonObject = new JSONObject(message);
////            if (!incomingJsonObject.isNull("data")) {
////                handleSchemaMessage(incomingJsonObject);
////            } else {
////                handleDataMessage(incomingJsonObject);
////            }
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
////
////    private void handleDataMessage(JSONObject incomingJsonObject) {
////        System.out.println(incomingJsonObject);
////    }
////
////
////    private void handleSchemaMessage(JSONObject incomingJsonObject) {
////        System.out.println(incomingJsonObject);
////    }
//
//}

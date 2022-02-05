package malangcute.bellytime.bellytimeCustomer.chat.domain;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.config.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) {
        System.out.println("sending via kafka");
        simpMessagingTemplate.convertAndSend("/topic/group", message);
    }
}

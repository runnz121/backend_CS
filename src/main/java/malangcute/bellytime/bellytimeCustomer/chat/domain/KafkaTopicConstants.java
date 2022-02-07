package malangcute.bellytime.bellytimeCustomer.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaTopicConstants {

    private String topic;

//    public KafkaTopicConstants(String topicName) {
//        this.topic = topicName;
//    }
//\
}

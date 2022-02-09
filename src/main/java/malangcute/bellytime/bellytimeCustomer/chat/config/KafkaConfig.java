//package malangcute.bellytime.bellytimeCustomer.chat.config;
//
//
//
//import com.google.common.collect.ImmutableMap;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.IntegerDeserializer;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.aspectj.bridge.Message;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.Map;
//import java.util.UUID;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
//
//    @Bean
//    public ProducerFactory<String, MessageDto> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<MessageDto>());
//
//    }
//
//    @Bean
//    public KafkaTemplate<String, MessageDto> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//
//        return ImmutableMap.<String, Object>builder()
//                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
//                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
//                .put("group.id", "main")
//                //.put("group.id", "spring-boot-test") // needed but don't know why...
//
//                .build();
//    }
//
//
//    //리스너 설정
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, MessageDto> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, MessageDto> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(MessageDto.class));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        return ImmutableMap.<String, Object>builder()
//                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//                //.put(ConsumerConfig.GROUP_ID_CONFIG, "main-listener")
//                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  StringDeserializer.class)
//                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
//                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
//
//               // .put("group.id", "spring-boot-test") // needed but dont know why...
//
//                .build();
//    }
//
//
//    //토픽 생성 설정
//    @Bean
//    public NewTopic createTopic() {
//        String randomId = UUID.randomUUID().toString();
//        return TopicBuilder
//                .name("topic"+randomId)
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//}
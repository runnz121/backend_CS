//package malangcute.bellytime.bellytimeCustomer.chat.config;
//
//
//
//import com.google.common.collect.ImmutableMap;
//import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
//
//    // group Id 는 나중에 추가 해주는 식으로(방마다 다를 것이기 때문)
//
//    /**
//     * producer Config
//     */
//
//    @Bean
//    public ProducerFactory<String, MessageDto> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfiguration());
//    }
//
//    @Bean
//    public KafkaTemplate<String, MessageDto> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    //map에서 immutablemap으로 변경
//    @Bean
//    public Map<String, Object> producerConfiguration() {
//
//        return ImmutableMap.<String, Object>builder()
//                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafKaConstants.KAFKA_BROKER)
//                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
//                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
//                .build();
//
//    }
////        Map<String, Object> configurations = new HashMap<>();
////        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
////        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
////        return configurations;
//
//
//    /**
//     * Listener(consumer) Config
//     */
//
//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String, MessageDto> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, MessageDto> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfiguration(), new StringDeserializer(), new JsonDeserializer<>(MessageDto.class));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfiguration() {
//        return ImmutableMap.<String, Object>builder()
//                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafKaConstants.KAFKA_BROKER)
//                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
//                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
//                .build();
//    }
//
//    /**
//     *  kafka admin -> topic 이름 다이나믹하게 생성
//     */
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        return new KafkaAdmin(ImmutableMap.<String,Object>builder()
//                .put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafKaConstants.KAFKA_BROKER)
//                .build()
//        );
//    }
//
//    @Bean
//    public NewTopic newTopic(){
//        String name = UUID.randomUUID().toString();
//        return new NewTopic(name, 1, (short)1);
//    }
//
//
//
////    @Bean
////    public ConsumerFactory<String, MessageDto> stockChangeConsumer() {
////
////        Map<String, Object> props = new HashMap<>();
////        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafKaConstants.KAFKA_BROKER);
////        props.put(ConsumerConfig.GROUP_ID_CONFIG, "sampler");
////
////        return new DefaultKafkaConsumerFactory<>(
////                props,
////                new StringDeserializer(),
////                // new JsonDeserializer<>(StockChangeDto.class)
////                new ErrorHandlingDeserializer(new JsonDeserializer<>(MessageDto.class))
////        );
////    }
//}
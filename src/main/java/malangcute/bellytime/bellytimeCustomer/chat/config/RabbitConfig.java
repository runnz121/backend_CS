//package malangcute.bellytime.bellytimeCustomer.chat.config;
//
//import com.fasterxml.jackson.databind.Module;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.nimbusds.jose.shaded.json.JSONObject;
//import com.rabbitmq.client.AMQP;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.MessageProperties;
//import com.rabbitmq.client.impl.AMQBasicProperties;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//
//@Configuration
//@EnableRabbit
//public class RabbitConfig {
//
//    private static final String CHAT_QUEUE_NAME = "chat.queue";
//    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
//    private static final String ROUTING_KEY = "room.*";
//    private final static String CHAT_TOPIC = "amq.topic";
//    private static final String MESSAGE_PERSIST = "PERSISTENT_TEXT_PLAIN";
//
//
//
//    //Queue 등록
//    @Bean
//    public Queue queue(){ return new Queue(CHAT_QUEUE_NAME, true); }
//
//    //Exchange 등록
//    @Bean
//    public TopicExchange exchange(){ return new TopicExchange(CHAT_EXCHANGE_NAME,true,false); }
//
//    //Exchange와 Queue 바인딩
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//    }
//
//
//    /* messageConverter를 커스터마이징 하기 위해 Bean 새로 등록 */
//    @Bean
//    public RabbitTemplate rabbitTemplate() throws IOException, TimeoutException {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        rabbitTemplate.setRoutingKey(CHAT_QUEUE_NAME);
//
//        return rabbitTemplate;
//    }
//
////    @Bean
////    public SimpleMessageListenerContainer container(){
////        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
////        container.setConnectionFactory(connectionFactory());
////        container.setQueueNames(CHAT_QUEUE_NAME);
////        //container.setMessageListener(null);
////        return container;
////    }
////
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        factory.setMessageConverter(jsonMessageConverter());
//        return factory;
//    }
//
//    //Spring에서 자동생성해주는 ConnectionFactory는 SimpleConnectionFactory인가? 그건데
//    //여기서 사용하는 건 CachingConnectionFacotry라 새로 등록해줌
//    @Bean
//    public ConnectionFactory connectionFactory()  {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost("localhost");
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        return factory;
//    }
//
////    @Bean
////    public com.rabbitmq.client.ConnectionFactory factory () {
////        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
////
////        return new com.rabbitmq.client.ConnectionFactory();
////    }
////
//
//
////    @Bean
////    public com.rabbitmq.client.ConnectionFactory connectionFactory1() throws IOException, TimeoutException {
////        com.rabbitmq.client.ConnectionFactory factory1 = new com.rabbitmq.client.ConnectionFactory();
////        Connection connection = factory1.newConnection();
////        Channel channel = connection.createChannel();
////        channel.queueDeclare(CHAT_QUEUE_NAME, true, false, false, null);
////        channel.basicPublish(CHAT_EXCHANGE_NAME,null,MessageProperties.PERSISTENT_TEXT_PLAIN, null);
////
////        return (com.rabbitmq.client.ConnectionFactory) channel;
////    }
//
//
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter(){
//        //LocalDateTime serializable을 위해
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        objectMapper.registerModule(dateTimeModule());
//
//
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
//
//        return converter;
//    }
//
//
//
////    @Bean
////    public Jackson2JsonMessageConverter jsonMessageConverter(){
////      return new Jackson2JsonMessageConverter();
////    }
//
//
//
//
//    @Bean
//    public Module dateTimeModule(){
//        return new JavaTimeModule();
//    }
//}
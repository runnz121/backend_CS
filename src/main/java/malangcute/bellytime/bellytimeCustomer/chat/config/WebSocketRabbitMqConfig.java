//package malangcute.bellytime.bellytimeCustomer.chat.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketRabbitMqConfig implements WebSocketMessageBrokerConfigurer {
//    /**
//     *  기본 stmop 설정
//     */
//
////    @Override
////    public void registerStompEndpoints(StompEndpointRegistry registry) {
////        registry.addEndpoint("/chat/chatting") // -> 리엑트와 연결되는 부분
////                .setAllowedOrigins("http://localhost:3000")
////                .withSockJS();
////    }
////    @Override
////    public void configureMessageBroker(MessageBrokerRegistry registry) {
////       // registry.enableSimpleBroker("/topic","/queue"); // topic -> 전체 유저, queue -> 1:1 특정 유저
////        registry.enableSimpleBroker("/sub");
////        registry.setApplicationDestinationPrefixes("/pub");
////    }
//
//
//    /**
//     *
//     * rabbitmq 적용
//     */
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat/chatting")
//               // .setAllowedOrigins("http://localhost:3000","http://localhost:3001")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/pub");
//        registry.setPathMatcher(new AntPathMatcher("."));
//
//        //외부 브로커 사용
//        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
//    }
//}
//

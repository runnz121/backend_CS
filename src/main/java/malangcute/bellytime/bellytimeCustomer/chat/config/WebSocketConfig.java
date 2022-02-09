//package malangcute.bellytime.bellytimeCustomer.chat.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
////
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //registry.enableSimpleBroker("/topic");//sub용 sub topic/public
//        registry.setApplicationDestinationPrefixes("/app");//root url send /app/message
//        registry.setUserDestinationPrefix("/user"); //특정 유저한테 보낼때 프리픽스
//        registry.enableStompBrokerRelay("/topic");
//    }
//
//    // react socket_url 과 매칭되는 부분
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chatting").setAllowedOriginPatterns("*").withSockJS();// URL//chatting  <-웹소켓 연결 주소
//    }
//
//
//
//
//    //rabbitmq 전용
////    @Override
////    public void configureMessageBroker(MessageBrokerRegistry registry) {
////        registry.setPathMatcher(new AntPathMatcher(".")); //  / 를 .로 변경
////        registry.setApplicationDestinationPrefixes("/pub");// 메세지 생산 client에서 send 요청 처리
////
////        //registry.enableSimpleBroker("/sub"); // 메세지소비, 이 경로로 simplebroker 등록
////        //해당 경로를 구독하는 client에게 메세지 전달
////
////        registry.enableStompBrokerRelay("/queue","/topic","/exchange","/amq/queue");
////    }
////
////    //채팅 클라이언트가 서버와 연결하웹소켓 셋팅 부분 ->웹소켓 연결 주소 -> /url/chatting
////    @Override
////    public void registerStompEndpoints(StompEndpointRegistry registry) {
////        // 웹소켓 핸드쉐이크 컨넥션 생성할 주소
////        registry.addEndpoint("/stomp/chat")
////                .setAllowedOrigins("*")
////                .withSockJS();
////    }
//}

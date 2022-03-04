package malangcute.bellytime.bellytimeCustomer.chat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); // 메시지 보낼 Url : /app/message
        registry.enableSimpleBroker("/sub"); //sub용 sub topic/public
    }

    //채팅 클라이언트가 서버와 연결하웹소켓 셋팅 부분 ->웹소켓 연결 주소 -> /url/chatting
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/chatting").setAllowedOriginPatterns("*").withSockJS();
    }

}
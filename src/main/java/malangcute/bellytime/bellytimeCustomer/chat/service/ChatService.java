package malangcute.bellytime.bellytimeCustomer.chat.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.config.KafkaConfig;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Message;
import malangcute.bellytime.bellytimeCustomer.chat.dto.CreateRoomWithRequest;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRepository;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final KafkaTemplate<String, Message> kafkaTemplate;

    private final KafkaConfig kafkaConfig;

    //메시지 보내기
    public void sendMsg(User user, Message message) {
        Long userId = user.getId();

        chatRepository.findChatRoom(userId) {

        }


        try {
            kafkaTemplate.send()
        }
    }


    @Transactional
    public String createRoomService(User user, CreateRoomWithRequest request) {
        User withUser = userRepository.findByEmail(new Email(request.getListenerName())).orElseThrow(() -> new UserIdNotFoundException("유저가 없습니다"));
        Long roomOwner = withUser.getId();
        Long consumer = user.getId();
        String roomName = kafkaConfig.newTopic().name();


        return roomName;
    }

}

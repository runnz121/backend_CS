package malangcute.bellytime.bellytimeCustomer.chat.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.config.KafkaConfig;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRepository;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserIdNotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
@ToString
@Transactional
@Slf4j
public class ChatService {

    private final KafkaConfig kafkaConfig;

    private final Receiver receiver;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;


    //해당 유저와의 방이 존재하는지방인지 조회
//    public GetRoomAndGroupDto checkRoomExist(Long roomOwner, Long consumer) {
//        Optional<GetRoomAndGroupDto> checkRoom = chatRepository.findChatRoom(roomOwner, consumer);
//        if (checkRoom.isPresent()) {
//            return checkRoom.orElseThrow(()-> new UserIdNotFoundException("no room"));
//        }
//        createRoomService(roomOwner, consumer)
//    }



    // 방이 있는지 조회 -> fetchjoin으로 수정 필요
    @Transactional
    public GetRoomAndGroupDto checkRoomExist(User userOwner, CreateRoomWithRequest request) {
        User withUser = userRepository.findByEmail(new Email(request.getListenerName())).orElseThrow(() -> new UserIdNotFoundException("유저가 없습니다"));
        Optional<GetRoomAndGroupFromRepo> checkUserWith = chatRepository.findChatRoom(userOwner.getId(), withUser.getId());
        GetRoomAndGroupDto returnDto = null;

        try {
            GetRoomAndGroupFromRepo roomCheck = checkUserWith.orElseThrow(()->new NotFoundException("방이 없습니다"));
            returnDto = GetRoomAndGroupDto.builder()
                    .roomId(roomCheck.getRoomName())
                    .groupId(roomCheck.getGroupId())
                    .build();

        } catch(NotFoundException ex) {
            returnDto = createRoom(userOwner, withUser);
        }

        return returnDto;

    }

    //방이 없을 경우 생성
    private GetRoomAndGroupDto createRoom(User userOwner, User withUser) {
        Long roomOwner = userOwner.getId();
        Long consumer = withUser.getId();

        String roomName = kafkaConfig.createTopic().name();
        String groupId = roomName + roomOwner + consumer;

        Chat newRoom = Chat.builder()
                .roomOwner(userOwner)
                .consumer(withUser)
                .roomName(roomName)
                .groupId(groupId)
                .build();

        chatRepository.save(newRoom);
        return GetRoomAndGroupDto.of(roomName, groupId);
    }


    // 유저아이디 기반으로 토픽 라우팅
//    public String topicDistribute(String roomId, MessageDto message) {
//        receiver.
//    }
}

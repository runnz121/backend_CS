package malangcute.bellytime.bellytimeCustomer.chat.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;
import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatLogRepository;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRepository;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@ToString
@Transactional
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final ChatLogRepository chatLogRepository;

    private final UserService userService;

    private static final String DELIMITER = "_";

    private static final String CUSTOMER = "customer";

    private static final String SHOP = "shop";


    //채팅방생성
    public RoomIdResponse createRoomService(User user, CreateRoomRequest createRoomRequest) {
        Chat makeRoom = Chat.create(
                generateRoom(user),
                generateRoomName(userService.findUserById(createRoomRequest.getInviteId())),
                createRoomRequest.getType(),
                user,
                userService.findUserById(createRoomRequest.getInviteId())
        );
        chatRepository.save(makeRoom);
        return RoomIdResponse.of(makeRoom);
    }

    //roomID uuid로 생성
    private String generateRoom(User user) {
        return user.getId() + DELIMITER + UUID.randomUUID();
    }

    //방 이름 생성
    private String generateRoomName(User user) {
        return user.getNickname().getNickName();
    }


    //내가 주인인 방의 친구 목록 반환
    public List<ChatRoomFriendListResponse> friendChatRoomList(User user) {
        return chatRepository.findByMakerIdOrInviteIdAndType(user, user, CUSTOMER)
                .stream()
                .map(it -> new ChatRoomFriendListResponse(
                        it.getRoomId(),
                        it.getInviteId().getId(),
                        it.getRoomName(),
                        it.getInviteId().getProfileImg()
                ))
                .collect(Collectors.toList());
    }

    //내가 주인인 방의 샵 목록 반환
    public List<ChatRoomShopListResponse> shopChatRoomList(User user) {
        return chatRepository.findByMakerIdOrInviteIdAndType(user, user, SHOP)
                .stream()
                .map(it -> new ChatRoomShopListResponse(
                        it.getRoomId(),
                        it.getInviteId().getId(),
                        it.getRoomName(),
                        it.getInviteId().getProfileImg()
                ))
                .collect(Collectors.toList());
    }


    //채팅방 삭제
    public void deleteRoomService(User user, String roomId) {
        chatRepository.deleteByRoomId(roomId);
    }


    //채팅 로그 저장
    public void saveLog(MessageDto messageDto) {
        ChatLog log = ChatLog.create(messageDto);
        chatLogRepository.save(log);
    }

    //채팅방 로그 반환하기
    public List<MessageDto> getChatLog(RoomIdRequest request) {
        return chatLogRepository.findByRoomId(request.getRoomId()).stream()
                .sorted(Comparator.comparing(ChatLog::getCreatedAt))
                .map(MessageDto::of)
                .collect(Collectors.toList());
    }

}

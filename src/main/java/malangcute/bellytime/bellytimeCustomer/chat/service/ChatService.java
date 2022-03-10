package malangcute.bellytime.bellytimeCustomer.chat.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;
import malangcute.bellytime.bellytimeCustomer.chat.dto.*;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatLogRepository;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRepository;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRoomSearchStrategy;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRoomSearchStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import net.minidev.json.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatLogRepository chatLogRepository;

    private final ChatRoomSearchStrategyFactory chatRoomSearchStrategyFactory;

    private final UserService userService;

    private static final String DELIMITER = "_";

    private static final String CUSTOMER = "customer";

    private static final String SHOP = "shop";

    private static final String PERSON_DELIMITER = ", ";



    public RoomIdResponse checkExistsRoomId(User user, CreateRoomRequest createRoomRequest) {
        if (createRoomRequest.getInviteId().size() == 1) {
            try {
                Long invitedId = createRoomRequest.getInviteId().get(0);
                RoomIdResponse response =  chatRoomSearchStrategyFactory.findStrategy(createRoomRequest.getType())
                        .searchRoomWithType(user, invitedId, createRoomRequest.getType());
                if (response.getRoomId().isEmpty()) {
                    return createRoomService(user, createRoomRequest);
                }
                return response;
            } catch(NullPointerException ex) {
                return createRoomService(user, createRoomRequest);
            }
        }
       return createRoomService(user, createRoomRequest);
    }



    //채팅방생성
    @Transactional
    public RoomIdResponse createRoomService(User user, CreateRoomRequest createRoomRequest) {
        List<Chat> makeRooms = new ArrayList<>();
        String roomId = generateRoom(user);
     //   String roomName = generateRoomName(createRoomRequest);
        for (Long invitedId : createRoomRequest.getInviteId()) {
          Chat makeRoom = Chat.builder()
                  .roomId(roomId)
                  .roomName(createRoomRequest.getRoomName())
                  .type(createRoomRequest.getType())
                  .makerId(user)
                  .inviteId(userService.findUserById(invitedId))
                  .build();
            makeRooms.add(makeRoom);
        }
        chatRepository.saveAll(makeRooms);
        return RoomIdResponse.of(roomId);
    }


    //내가 주인인 방의 친구 목록 반환
    public List<ChatRoomFriendListResponse> friendChatRoomList(User user) {
        Map<String, List<ChatContactIdAndImgDto>> friendList = chatRepository.findByMakerIdAndType(user, CUSTOMER).stream()
                .collect(Collectors.groupingBy(
                        Chat::getRoomId,
                        Collectors.mapping(ChatContactIdAndImgDto::of, Collectors.toList())));

        return chatRepository.findByMakerIdAndType(user, CUSTOMER)
                .stream().filter(distinctByKey(Chat::getRoomId))
                .map(it -> ChatRoomFriendListResponse.of(friendList, it, getLastContent(user,it.getRoomId())))
                .collect(Collectors.toList());

    }


    //내가 주인인 방의 샵 목록 반환
    public List<ChatRoomShopListResponse> shopChatRoomList(User user) {

        Map<String, List<ChatContactIdAndImgDto>> shopList = chatRepository.findByMakerIdAndType(user, SHOP).stream()
                .collect(Collectors.groupingBy(
                        Chat::getRoomId,
                        Collectors.mapping(ChatContactIdAndImgDto::of, Collectors.toList())));

        return chatRepository.findByMakerIdAndType(user, SHOP)
                .stream()
                .filter(distinctByKey(Chat::getRoomId))
                .map(it -> ChatRoomShopListResponse.of(shopList,it, getLastContent(user,it.getRoomId())))
                .collect(Collectors.toList());
    }


    //채팅방 삭제
    public MessageDto deleteRoomService(User user, String roomId) {
        chatRepository.deleteByRoomIdAndInviteId(roomId, user);
        return MessageDto.exit(roomId, user.getNickname().getNickName());
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

    //채팅방 마지막 로그 반환하기
    @Transactional(readOnly = true)
    public String getLastContent(User user, String roomId) {

        ChatLog log = chatLogRepository.findBySenderAndRoomId(user.getId(), roomId)
                .stream().sorted(Comparator.comparing(ChatLog::getCreatedAt))
                .findAny().orElseGet(ChatLog::empty);
        return log.getMessage();
    }

    //친구추가하기
    public void addFriend(User user, CreateRoomRequest request) {
        List<Chat> makeRooms = new ArrayList<>();
        String roomId = generateRoom(user);
        String userRoomName = generateRoomName(request);
        for (Long invitedId : request.getInviteId()) {
            Chat makeRoom = Chat.builder()
                    .roomId(roomId)
                    .roomName(userRoomName)
                    .type(CUSTOMER)
                    .makerId(user)
                    .inviteId(userService.findUserById(invitedId))
                    .build();
            makeRooms.add(makeRoom);
        }
        chatRepository.saveAll(makeRooms);
    }

    //중복제거
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; }

    //roomID uuid로 생성
    private String generateRoom(User user) {
        return user.getId() + DELIMITER + UUID.randomUUID();
    }

    //방 이름 생성
    private String generateRoomName(CreateRoomRequest request) {
        StringBuilder roomWithName = new StringBuilder();
        for (Long id : request.getInviteId()) {
            String eachUser = userService.findUserById(id).getNickname().getNickName();
            roomWithName.append(eachUser);
            roomWithName.append(PERSON_DELIMITER);
        }
        return roomWithName.toString().replaceAll(",$", "");
    }
}

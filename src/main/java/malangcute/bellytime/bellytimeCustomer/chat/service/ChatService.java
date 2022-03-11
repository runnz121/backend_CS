package malangcute.bellytime.bellytimeCustomer.chat.service;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatContactIdAndImgDto;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatImgDtoGroupingKey;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomInviteFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomShopListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.CreateRoomRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatLogRepository;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRepository;
import malangcute.bellytime.bellytimeCustomer.chat.repository.ChatRoomSearchStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

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
                        .searchRoomWithType(invitedId, createRoomRequest.getType());
                if (response.getRoomId().isEmpty()) {
                    return createRoomService(user, createRoomRequest);
                }
                return response;
            } catch (NullPointerException ex) {
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
        createRoomRequest.getInviteId().add(user.getId());
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
        return RoomIdResponse.from(roomId);
    }


    //친구추가하기
    public void addFriend(User user, ChatRoomInviteFriendsRequest request) {
        List<Chat> makeRooms = new ArrayList<>();
        String roomName = chatRepository.findByRoomId(request.getRoomId()).getRoomName();
        for (Long invitedId : request.getInviteId()) {
            Chat makeRoom = Chat.builder()
                    .roomId(request.getRoomId())
                    .roomName(roomName)
                    .type(CUSTOMER)
                    .makerId(user)
                    .inviteId(userService.findUserById(invitedId))
                    .build();
            makeRooms.add(makeRoom);
        }
        chatRepository.saveAll(makeRooms);
    }

    public List<MessageDto> sendInvitedMessage(ChatRoomInviteFriendsRequest request) {
        List<MessageDto> list = new ArrayList<>();
        for (Long userId : request.getInviteId()) {
            User invited = userService.findUserById(userId);
            list.add(MessageDto.invite(invited, request.getRoomId()));
            saveLog(MessageDto.builder()
                    .roomId(request.getRoomId())
                    .sender(-2L)
                    .nickName(invited.getNickname().getNickName())
                    .message(invited.getNickname().getNickName() + " 님이 초대되었습니다.")
                    .build());
        }
        return list;
    }


    //내가 주인인 방의 친구 목록 반환
    public List<ChatRoomFriendListResponse> friendChatRoomList(User user) {

        Map<ChatImgDtoGroupingKey, List<ChatContactIdAndImgDto>> lists =  chatRepository.findMyChatList(user.getId(), CUSTOMER)
                .stream()
                .filter(it-> !Objects.equals(it.getContactId(), user.getId()))
                .collect(Collectors.groupingBy(
                        ChatImgDtoGroupingKey::new,
                        Collectors.mapping(ChatContactIdAndImgDto::from, Collectors.toList())
                ));
        return lists.entrySet()
               .stream()
               .map(it -> ChatRoomFriendListResponse.from(it, getLastContent(user, it.getKey().getRoomId()))).collect(toList());
    }


    //내가 주인인 방의 샵 목록 반환
    public List<ChatRoomShopListResponse> shopChatRoomList(User user) {
        Map<ChatImgDtoGroupingKey, List<ChatContactIdAndImgDto>> lists =  chatRepository.findMyChatList(user.getId(), SHOP)
                .stream()
                .filter(it-> !Objects.equals(it.getContactId(), user.getId()))
                .collect(Collectors.groupingBy(
                        ChatImgDtoGroupingKey::new,
                        Collectors.mapping(ChatContactIdAndImgDto::from, Collectors.toList())
                ));
        return lists.entrySet()
                .stream()
                .map(it -> ChatRoomShopListResponse.from(it, getLastContent(user, it.getKey().getRoomId()))).collect(toList());
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


    //중복제거
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

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

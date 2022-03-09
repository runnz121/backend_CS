package malangcute.bellytime.bellytimeCustomer.chat.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomSearchFriend implements ChatRoomSearchStrategy{

    private final static String TYPE = "customer";

    private final ChatRepository chatRepository;

    @Override
    public boolean searchBy(String type) {
        return TYPE.equals(type);
    }

    @Override
    public RoomIdResponse searchRoomWithType(User user, Long inviteId, String type) {
        System.out.println("customer 타입 선택이고 이미 있는지 확인함");
       String friendRoomId = chatRepository.findSingleRoomIdExist(user.getId(), inviteId, type);
        return RoomIdResponse.of(friendRoomId);
    }
}

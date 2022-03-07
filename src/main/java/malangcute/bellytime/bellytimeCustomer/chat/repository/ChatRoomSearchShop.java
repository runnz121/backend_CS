package malangcute.bellytime.bellytimeCustomer.chat.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomSearchShop implements ChatRoomSearchStrategy {

    private final static String TYPE = "shop";

    private final ChatRepository chatRepository;

    @Override
    public boolean searchBy(String type) {
        return TYPE.equals(type);
    }

    @Override
    public RoomIdResponse searchRoomWithType(User user, Long inviteId, String type) {
       String shopRoomId = chatRepository.findSingleRoomIdExist(user.getId(), inviteId, type);
        return RoomIdResponse.of(shopRoomId);
    }
}

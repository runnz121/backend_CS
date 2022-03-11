package malangcute.bellytime.bellytimeCustomer.chat.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@Component
@RequiredArgsConstructor
public class ChatRoomSearchShop implements ChatRoomSearchStrategy {

    private static final String TYPE = "shop";

    private final ChatRepository chatRepository;

    @Override
    public boolean searchBy(String type) {
        return TYPE.equals(type);
    }

    @Override
    public RoomIdResponse searchRoomWithType(Long inviteId, String type) {
        //String shopRoomId = chatRepository.findSingleRoomIdExist(inviteId, type);
        Chat chat = chatRepository.findSingleRoomIdExist(inviteId, type);
        return RoomIdResponse.of(chat);
    }
}

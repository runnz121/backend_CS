package malangcute.bellytime.bellytimeCustomer.chat.repository;

import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

public interface ChatRoomSearchStrategy {

    boolean searchBy(String type);

    RoomIdResponse searchRoomWithType(User user, Long inviteId, String type);
}

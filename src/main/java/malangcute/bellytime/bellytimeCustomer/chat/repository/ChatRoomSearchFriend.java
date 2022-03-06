package malangcute.bellytime.bellytimeCustomer.chat.repository;

import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

public class ChatRoomSearchFriend implements ChatRoomSearchStrategy{

    private final static String TYPE = "customer";

    @Override
    public boolean searchBy(String type) {
        return TYPE.equals(type);
    }

    @Override
    public RoomIdResponse searchRoomWithType(User user, String type) {

    }


}

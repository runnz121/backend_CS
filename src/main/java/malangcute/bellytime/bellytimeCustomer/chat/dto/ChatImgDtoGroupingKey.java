package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ChatImgDtoGroupingKey {

    private String roomId;

    private String roomName;

    public ChatImgDtoGroupingKey(ChatListResponseIF list) {
        this.roomId = list.getRoomId();
        this.roomName = list.getRoomName();
    }
}

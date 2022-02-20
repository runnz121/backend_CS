package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomIdResponse {

    private String roomId;

    public static RoomIdResponse of (Chat chat) {
        return new RoomIdResponse(chat.getRoomId());
    }
}

package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomIdResponse {

    private String roomId;

    public static RoomIdResponse of(Chat chat) {
        return new RoomIdResponse(chat.getRoomId());
    }

    public static RoomIdResponse from(String roomId) {
        return new RoomIdResponse(roomId);
    }
}

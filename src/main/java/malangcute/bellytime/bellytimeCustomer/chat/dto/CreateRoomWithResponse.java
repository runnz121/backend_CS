package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRoomWithResponse {

    private String roomId;

    private String groupId;

    public static CreateRoomWithResponse of (String roomId, String groupId) {
        return new CreateRoomWithResponse(roomId, groupId);
    }
}

package malangcute.bellytime.bellytimeCustomer.chat.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomShopListResponse {

    private String chatRoomId;

    private String roomName;

    private List<ChatContactIdAndImgDto> contact = new ArrayList<>();

    private String recentContent;

    public static ChatRoomShopListResponse from(Map.Entry<ChatImgDtoGroupingKey, List<ChatContactIdAndImgDto>> lists, String recentContent) {
        return new ChatRoomShopListResponse(lists.getKey().getRoomId(), lists.getKey().getRoomName(), lists.getValue(), recentContent);
    }
}

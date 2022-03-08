package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomShopListResponse {

    private String chatRoomId;

    private String roomName;

    private List<ChatContactIdAndImgDto> contact = new ArrayList<>();

    private String recentContent;

    public static ChatRoomShopListResponse of (Map<String, List<ChatContactIdAndImgDto>> lists, Chat chat, String content) {
        List<ChatContactIdAndImgDto> list = lists.get(chat.getRoomId());
        return new ChatRoomShopListResponse(chat.getRoomId(), chat.getRoomName(), list, content);
    }
}

package malangcute.bellytime.bellytimeCustomer.chat.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomFriendListResponse {

    private String chatRoomId;

    private String roomName;

    private List<ChatContactIdAndImgDto> contact;

    private String recentContent;


    public static ChatRoomFriendListResponse of (Map<String, List<ChatContactIdAndImgDto>> lists, Chat chat, String content) {
        List<ChatContactIdAndImgDto> list = lists.get(chat.getRoomId());
        return new ChatRoomFriendListResponse(chat.getRoomId(), chat.getRoomName(), list, content);
    }

    public static ChatRoomFriendListResponse from (Map.Entry<ChatImgDtoGroupingKey, List<ChatContactIdAndImgDto>> lists, String recentContent) {
        return new ChatRoomFriendListResponse(lists.getKey().getRoomId(), lists.getKey().getRoomName(), lists.getValue(), recentContent);
    }

}

package malangcute.bellytime.bellytimeCustomer.chat.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomFriendListResponse {

    private String chatRoomId;

    private Long customerId;

    private String profileImg;

   // private String recentContent;

    public static ChatRoomFriendListResponse of(String chatRoomId, Long customerId, String profileImg) {
        return new ChatRoomFriendListResponse(chatRoomId, customerId, profileImg);
    }
}

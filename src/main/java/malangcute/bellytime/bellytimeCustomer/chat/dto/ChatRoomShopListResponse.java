package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomShopListResponse {

    private String chatRoomId;

    private Long shopId;

    private String roomName;

    private String profileImg;

  //  private String recentContent;
}

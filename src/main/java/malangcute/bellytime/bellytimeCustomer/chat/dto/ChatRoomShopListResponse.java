package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomShopListResponse {

    private String chatRoomId;

    private List<Long> shopId = new ArrayList<>();

    private String roomName;

    private List<String> profileImg = new ArrayList<>();

    private String recentContent;

    public static ChatRoomShopListResponse of (Chat chat, String lastChat) {

        // 한 유저는 여러개의 채팅방을 갖고 있다
        List<Long> listCustomer = chat.getInviteId().getInviteId()
                .stream().map(it -> it.getInviteId().getId()).collect(Collectors.toList());

        List<String> listProfileImg = chat.getInviteId().getInviteId()
                .stream().map(it -> it.getInviteId().getProfileImg()).collect(Collectors.toList());

        return new ChatRoomShopListResponse(chat.getRoomId(), listCustomer, chat.getRoomName(), listProfileImg, lastChat);
    }
}

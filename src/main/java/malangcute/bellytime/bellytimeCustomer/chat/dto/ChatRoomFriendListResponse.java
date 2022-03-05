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
public class ChatRoomFriendListResponse {

    private String chatRoomId;

   // private Long customerId;

    private List<Long> customerId = new ArrayList<>();

    private String roomName;

    //private String profileImg;

    private List<String> profileImg = new ArrayList<>();

    private String recentContent;

//    public static ChatRoomFriendListResponse of(String chatRoomId, Long customerId, String roomName,String profileImg) {
//        return new ChatRoomFriendListResponse(chatRoomId, customerId, roomName, profileImg);
//    }


    public static ChatRoomFriendListResponse of (Chat chat, String lastChat) {

        // 한 유저는 여러개의 채팅방을 갖고 있다
        List<Long> listCustomer = chat.getInviteId().getInviteId()
                .stream().map(it -> it.getInviteId().getId()).collect(Collectors.toList());

        List<String> listProfileImg = chat.getInviteId().getInviteId()
                .stream().map(it -> it.getInviteId().getProfileImg()).collect(Collectors.toList());

        return new ChatRoomFriendListResponse(chat.getRoomId(), listCustomer, chat.getRoomName(), listProfileImg, lastChat);
    }

}

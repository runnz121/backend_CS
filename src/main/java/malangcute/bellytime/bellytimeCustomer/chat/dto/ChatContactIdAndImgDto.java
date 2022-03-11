package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatContactIdAndImgDto {

    private Long contactId;

    private String profileImg;

    private String nickName;


    public static ChatContactIdAndImgDto from(ChatListResponseIF chat) {
        return new ChatContactIdAndImgDto(chat.getContactId(), chat.getProfileImg(), chat.getNickName());
    }

}

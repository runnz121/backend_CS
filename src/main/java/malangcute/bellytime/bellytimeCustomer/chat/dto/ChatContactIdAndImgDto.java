package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.Chat;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatContactIdAndImgDto {

    private Long contactId;

    private String profileImg;

    private String nickName;


    public static ChatContactIdAndImgDto from (ChatListResponseIF chat) {
        return new ChatContactIdAndImgDto(chat.getContactId(), chat.getProfileImg(), chat.getNickName());
    }

}

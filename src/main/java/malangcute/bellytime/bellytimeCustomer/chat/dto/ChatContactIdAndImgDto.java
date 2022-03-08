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


    public static ChatContactIdAndImgDto of (Chat chat) {
        return new ChatContactIdAndImgDto(chat.getInviteId().getId(), chat.getInviteId().getProfileImg());
    }
}

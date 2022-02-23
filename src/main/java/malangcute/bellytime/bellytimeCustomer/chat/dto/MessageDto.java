package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private String roomId;

    private Long sender;

    private String nickName;

    private String content;

    //private String message;

    public static MessageDto of (ChatLog log) {
        return new MessageDto(log.getRoomId(), log.getSender(),  log.getNickName(),log.getMessage());
    }

}
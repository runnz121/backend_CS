package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private String roomId;

    private Long sender;

    private String nickName;

    private String content;

    private String sendTime;

    public MessageDto(String roomId, Long sender, String nickName, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.nickName = nickName;
        this.content = message;


    }


    public static MessageDto of (ChatLog log) {
        return new MessageDto(log.getRoomId(), log.getSender(), log.getNickName(),log.getMessage());
    }

}
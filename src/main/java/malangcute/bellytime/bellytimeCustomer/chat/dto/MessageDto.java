package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class MessageDto {

    private String roomId;

    private Long sender;

    private String nickName;

    private String content;

    private String sendTime;


    public MessageDto(String roomId, Long sender, String nickName, String message, String sendTime) {
        this.roomId = roomId;
        this.sender = sender;
        this.nickName = nickName;
        this.content = message;
        this.sendTime = sendTime;
    }


    public MessageDto(String roomId, Long sender, String nickName, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.nickName = nickName;
        this.content = message;
    }


    public static MessageDto of (ChatLog log) {
        return new MessageDto(log.getRoomId(), log.getSender(), log.getNickName(),log.getMessage());
    }

    public static MessageDto send (MessageDto send) {
        return new MessageDto(send.getRoomId(), send.getSender(), send.getNickName(), send.getContent(), send.getSendTime());
    }
}
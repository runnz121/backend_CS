package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

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

    private boolean filter;


    @Builder
    public MessageDto(String roomId, Long sender, String nickName, String message, String sendTime) {
        this.roomId = roomId;
        this.sender = sender;
        this.nickName = nickName;
        this.content = message;
        this.sendTime = sendTime;
    }

    public MessageDto(String roomId, Long sender, String nickName,  String content) {
        this.roomId = roomId;
        this.nickName = nickName;
        this.sender = sender;
        this.content = content;
    }


    public static MessageDto of (ChatLog log) {
        return new MessageDto(
                log.getRoomId(),
                log.getSender(),
                log.getNickName(),
                log.getMessage(),
                log.getCreatedAt().toString().replaceAll("T"," "));
    }

    public static MessageDto send (MessageDto send) {
        return new MessageDto(
                send.getRoomId(),
                send.getSender(),
                send.getNickName(),
                send.getContent(),
                send.getSendTime());
    }


    public static MessageDto exit (String roomId, String nickName) {
        return new MessageDtoBuilder()
                .roomId(roomId)
                .nickName(nickName)
                .message(nickName + " 님이 채팅방을 떠났습니다")
                .sender(-1L)
                .sendTime(null)
                .build();
    }
}
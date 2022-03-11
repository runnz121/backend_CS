package malangcute.bellytime.bellytimeCustomer.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.domain.ChatLog;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@Getter
@NoArgsConstructor
public class MessageDto {

    private String roomId;

    private Long sender;

    private String nickName;

    private String content;

    private String sendTime;

    private Long contactId;

    private String profileImg;


    @Builder
    public MessageDto(String roomId, Long sender, String nickName, String message, String sendTime, String profileImg, Long contactId) {
        this.roomId = roomId;
        this.sender = sender;
        this.nickName = nickName;
        this.content = message;
        this.sendTime = sendTime;
        this.contactId = contactId;
        this.profileImg = profileImg;
    }


    public static MessageDto of(ChatLog log) {
        return new MessageDtoBuilder()
                .roomId(log.getRoomId())
                .sender(log.getSender())
                .nickName(log.getNickName())
                .message(log.getMessage())
                .sendTime( log.getCreatedAt().toString().replaceAll("T"," "))
                .build();
    }

    public static MessageDto send(MessageDto send) {
        return new MessageDtoBuilder()
                .roomId(send.getRoomId())
                .sender(send.getSender())
                .nickName(send.getNickName())
                .message(send.getContent())
                .sendTime(send.getSendTime())
                .build();
    }

    public static MessageDto exit(String roomId, String nickName) {
        return new MessageDtoBuilder()
                .roomId(roomId)
                .nickName(nickName)
                .message(nickName + " 님이 채팅방을 떠났습니다")
                .sender(-1L)
                .sendTime(null)
                .build();
    }

    public static MessageDto invite(User user, String roomId) {
        return new MessageDtoBuilder()
                .roomId(roomId)
                .nickName(user.getNickname().getNickName())
                .message(user.getNickname().getNickName() + " 님이 채팅방에 초대되었습니다.")
                .sender(-2L)
                .sendTime(null)
                .contactId(user.getId())
                .profileImg(user.getProfileImg())
                .build();
    }

}

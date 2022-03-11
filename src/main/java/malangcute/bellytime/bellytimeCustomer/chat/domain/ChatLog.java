package malangcute.bellytime.bellytimeCustomer.chat.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private Long sender;

    private String message;

    private String nickName;


    @Builder
    public ChatLog(String roomId, Long sender, String content, String nickName) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = content;
        this.nickName = nickName;
    }

    public static ChatLog create(MessageDto messageDto) {
        return new ChatLogBuilder()
            .roomId(messageDto.getRoomId())
            .sender(messageDto.getSender())
            .content(messageDto.getContent())
            .nickName(messageDto.getNickName())
            .build();
    }

    public static ChatLog empty() {
        return new ChatLog(0L,"",0L,"","");
    }
}

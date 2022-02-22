package malangcute.bellytime.bellytimeCustomer.chat.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

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


    public ChatLog(String roomId, Long sender, String content) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = content;
    }

    public static ChatLog create(MessageDto messageDto) {
        return new ChatLog(messageDto.getRoomId(), messageDto.getSender(), messageDto.getContent());
    }
}

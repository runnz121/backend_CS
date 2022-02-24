package malangcute.bellytime.bellytimeCustomer.chat.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@AllArgsConstructor
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String roomName;

    private String type;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name ="makerId")
    private User makerId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name ="inviteId")
    private User inviteId;

    public Chat(String roomId, String roomName, String type, User makerId, User inviteId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.type = type;
        this.makerId = makerId;
        this.inviteId = inviteId;
    }


    public static Chat create(String roomId, String roomName, String type, User makerId, User inviteId) {
        return new Chat(roomId,roomName, type, makerId, inviteId);
    }


}

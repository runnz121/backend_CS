package malangcute.bellytime.bellytimeCustomer.chat.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String roomName;

    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makerId")
    private User makerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviteId")
    private User inviteId;

    @Builder
    public Chat(String roomId, String roomName, String type, User makerId, User inviteId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.type = type;
        this.makerId = makerId;
        this.inviteId = inviteId;
    }


    public static Chat create(String roomId, String roomName, String type, User makerId, User inviteId) {
        return new Chat(roomId, roomName, type, makerId, inviteId);
    }

    public void setInviteId(User inviteId) {
        this.inviteId = inviteId;
    }
}


package malangcute.bellytime.bellytimeCustomer.chat.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"roomOwner", "consumer"})
@ToString
@Getter
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    private String groupId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User roomOwner;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User consumer;

    @Builder
    public Chat (Long id, String roomName, String groupId, User roomOwner, User consumer) {
        this.id = id;
        this.roomName = roomName;
        this.groupId = groupId;
        this.roomOwner = roomOwner;
        this.consumer = consumer;
    }
}

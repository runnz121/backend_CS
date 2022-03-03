package malangcute.bellytime.bellytimeCustomer.follow.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FollowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "hostId")
    private User hostId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "friendId")
    private User friendId;


    public FollowUser (User hostId, User friendId) {
        this.hostId = hostId;
        this.friendId = friendId;
    }



    public static FollowUser create (User userA, User userB) {
        return new FollowUser(userA, userB);
    }
}

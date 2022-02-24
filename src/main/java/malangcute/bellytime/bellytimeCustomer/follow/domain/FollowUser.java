package malangcute.bellytime.bellytimeCustomer.follow.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
//@EqualsAndHashCode(exclude = {"hostId", "friendId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //한곳에서 같은거 바라보면 joincolumn 비워두기
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

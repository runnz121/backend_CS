package malangcute.bellytime.bellytimeCustomer.comment.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentWriteRequest;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long score;

    private String state;

    private String visible;

    private String content;


    //예약과 예약이 연관관계 주인
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;


    @OneToMany(mappedBy = "commentsId")
    private List<CommentImg> commentImgs = new ArrayList<>();

    @Builder
    public Comment (User user, CommentWriteRequest request, Reservation reservation, List<CommentImg> img) {
        this.score = request.getScore();
        this.state = request.getState();
        this.visible = request.getState();
        this.content = request.getContent();
        this.reservationId = reservation;
        this.userId = user;
        this.commentImgs = img;
    }

}

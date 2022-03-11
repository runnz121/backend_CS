package malangcute.bellytime.bellytimeCustomer.comment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentWriteRequest;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.reservation.domain.Reservation;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private Reservation reservationId;

    @ManyToOne(fetch = FetchType.EAGER)
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

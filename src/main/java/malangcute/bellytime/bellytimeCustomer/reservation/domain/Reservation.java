package malangcute.bellytime.bellytimeCustomer.reservation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.domain.Comment;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservedDate;

    private Long reservedPeople;

    private String reservedState;

    private String showCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shopId;


    //댓글과 예약의 연관관계
    @OneToMany(mappedBy ="reservationId")
    private List<Comment> commentId = new ArrayList<>();

}

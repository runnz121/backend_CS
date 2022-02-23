package malangcute.bellytime.bellytimeCustomer.cooltime.domain;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cooltime")
public class CoolTime extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "timetable_id")
    private Long id;

    //@Column(name = "cooltime_startdate")
    private LocalDateTime startDate;

    //@Column(name = "cooltime_enddate")
    private LocalDateTime endDate;

    //@Column(name = "gauge")
    private String gauge;

    private Integer duration;

    private Boolean eat;

    //@ManyToOne은 항상 연관관계의 주인이 되므로 mappedBy 설정이 불가 !
    //출처: https://data-make.tistory.com/611 [Data Makes Our Future]
    @ManyToOne
    //@JoinColumn(name = "user_id")//외래키를 맵핑하는 설정
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "food_id")
    private Food foodId;

    @Builder
    public CoolTime(Long id, LocalDateTime startDate, Integer duration, LocalDateTime endDate, String gauge, User userId, Food foodId, Boolean eat) {
        this.id= id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gauge = gauge;
        this.foodId = foodId;
        this.userId = userId;
        this.duration = duration;
        this.eat = eat;
    }


    //하루 더해서 반환
    public CoolTime endDateUpdate(LocalDateTime endDate) {
        this.endDate = endDate.plusDays(1);
        return this; //클래스를 반환
    }
}

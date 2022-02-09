package malangcute.bellytime.bellytimeCustomer.follow.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FollowShop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;


    @JoinColumn(name = "shop_id")
    @ManyToOne
    private Shop shop;
}

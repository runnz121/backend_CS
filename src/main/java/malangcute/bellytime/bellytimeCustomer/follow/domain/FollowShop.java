package malangcute.bellytime.bellytimeCustomer.follow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.common.BaseTimeEntity;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class FollowShop  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "shop_id")
    @ManyToOne
    private Shop shop;

    public FollowShop(User userId, Shop shopId) {
        this.user = userId;
        this.shop = shopId;
    }

    public static FollowShop create(User user, Shop shop) {
        return new FollowShop(user, shop);
    }
}

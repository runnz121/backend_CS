package malangcute.bellytime.bellytimeCustomer.follow.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyFollowShopResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    public static MyFollowShopResponse from(FollowShop followShop) {
        return new MyFollowShopResponse(
                followShop.getShop().getId(),
                followShop.getShop().getName(),
                followShop.getShop().getImage());
    }
}

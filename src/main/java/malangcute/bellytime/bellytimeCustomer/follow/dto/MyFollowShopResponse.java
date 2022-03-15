package malangcute.bellytime.bellytimeCustomer.follow.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopResultListDetailResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyFollowShopResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private int reviewCount;

    private Long score;

    private String address;

    private boolean status;

    private int followerCount;

    private boolean follow;


    public static MyFollowShopResponse of (Shop shop, int follower, int reviewCount, boolean status, boolean follow) {

        return new MyFollowShopResponse(
                shop.getId(),
                shop.getName(),
                shop.getImage(),
                reviewCount,
                shop.getBellscore(),
                shop.getAddress(),
                status,
                follower,
                follow
        );
    }
}

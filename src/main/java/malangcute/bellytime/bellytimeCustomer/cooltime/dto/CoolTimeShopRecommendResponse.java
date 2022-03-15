package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeShopRecommendResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private int reviewCount;

    private Long score;

    private String address;

    private boolean status;

    private int followerCount;

    private boolean follow;

    public static CoolTimeShopRecommendResponse of (Shop shop, int follower, int reviewCount, boolean status, boolean follow) {

        return new CoolTimeShopRecommendResponse(
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

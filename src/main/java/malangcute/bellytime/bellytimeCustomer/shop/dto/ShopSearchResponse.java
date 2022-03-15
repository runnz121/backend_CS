package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ShopSearchResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private int reviewCount;

    private Long score;

    private String address;

    private boolean status;

    private int followerCount;

    private boolean follow;

    public static ShopSearchResponse of (Shop shop, boolean status, int followerCount, int reviewCount, boolean follow) {

        return new ShopSearchResponse(
                shop.getId(),
                shop.getName(),
                shop.getImage(),
                reviewCount,
                shop.getBellscore(),
                shop.getAddress(),
                status,
                followerCount,
                follow
        );
    }
}

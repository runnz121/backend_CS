package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ShopSearchResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private Long score;

    private String address;

    private boolean status;

    public static ShopSearchResponse of (Shop shop, boolean status) {

        return new ShopSearchResponse(
                shop.getId(),
                shop.getName(),
                shop.getImage(),
                shop.getBellscore(),
                shop.getAddress(),
                status
        );
    }
}

package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopResultListDetailResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private Long score;

    private String address;

    private int followCounter;

    private boolean status;



    public static ShopResultListDetailResponse of (Shop shop, int followCounter, boolean status) {

        return new ShopResultListDetailResponse(
                shop.getId(),
                shop.getName(),
                shop.getImage(),
                shop.getBellscore(),
                shop.getAddress(),
                followCounter,
                status
        );
    }
}

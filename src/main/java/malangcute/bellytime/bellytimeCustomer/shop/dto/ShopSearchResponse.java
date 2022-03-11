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

    private Long score;

    private String address;

    private boolean status;

    public ShopSearchResponse(Long id, String name) {
        this.shopId = id;
        this.shopName = name;
    }

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

    public static ShopSearchResponse from (ShopDetailDto dto) {

        return new ShopSearchResponse(
            dto.getId(),
            dto.getName()
        );
    }
}

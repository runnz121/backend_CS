package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ShopSearchResultListDto {

    private Long shop_id;

    private String shop_name;

    private String profile_img;

    private Long score;

    private String address;

    private String runtime;

//    private LocalDateTime create_at;
//
//    private LocalDateTime modified_at;

    public static ShopSearchResultListDto from (Shop shop) {
        return new ShopSearchResultListDto(shop.getId(), shop.getName(), shop.getImage(), shop.getScore(), shop.getAddress(), shop.getRuntime());
    }

}

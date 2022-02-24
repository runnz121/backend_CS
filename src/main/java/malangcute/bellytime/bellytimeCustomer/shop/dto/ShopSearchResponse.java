package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ShopSearchResponse {

    private Long shop_id;

    private String shop_name;

    private String profile_img;

   // private Long review_count;

    private Long score;

    private String address;

    private String runtime;

    // 리스트로 변환해야함
  //  private String menu;

  //  private Long follower_count;

    public static ShopSearchResponse from (ShopSearchResultListDto dto) {
        return new ShopSearchResponse(dto.getShop_id(), dto.getShop_name(), dto.getProfile_img(), dto.getScore(), dto.getAddress(), dto.getRuntime());
    }
}

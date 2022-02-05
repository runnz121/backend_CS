package malangcute.bellytime.bellytimeCustomer.shop.dto;


import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@AllArgsConstructor
public class ShopSaveRequest {

    private String name;

    private String img;

    private String address;

}

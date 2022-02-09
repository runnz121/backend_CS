package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchShopResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private Long reviewCount;

    private Long score;

    private String address;

    private String runtime;

    private List<String> menu;

    private Long followerCount;
}

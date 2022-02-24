package malangcute.bellytime.bellytimeCustomer.search.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchShopRequest {

    private String name;

    private String sortBy;
}

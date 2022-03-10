package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListWithMenuResponse;

import java.util.List;

//공통기능 서술
public interface ShopSortStrategy {

    // 분류기준중에 하나가 왔는지 체크
    boolean sortedBy(String type);

    List<ShopSearchResultListWithMenuResponse> SortedList (String name);
}

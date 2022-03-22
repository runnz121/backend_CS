package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import java.util.List;

//공통기능 서술
public interface ShopSortStrategy {

    // 분류기준중에 하나가 왔는지 체크
    boolean sortedBy(String type);

    List<ShopSearchResultListResponse> SortedList (User user, String name);
}

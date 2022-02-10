package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;

import java.util.List;

public interface ShopSortStrategy {

    boolean sortedBy(String type);

    List<ShopSearchResultListDto> SortedList (String name);
}

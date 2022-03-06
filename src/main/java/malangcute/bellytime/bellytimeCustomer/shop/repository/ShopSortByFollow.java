package malangcute.bellytime.bellytimeCustomer.shop.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 특별 기능 서술
@Component
@RequiredArgsConstructor
public class ShopSortByFollow implements ShopSortStrategy{

    private static final String TYPE = "follow";

    private final ShopRepository shopRepository;

    @Override
    public boolean sortedBy(String type) {
        return TYPE.equals(type);
    }


    @Override
    public List<ShopSearchResultListDto> SortedList(String name) {
        List<ShopSearchResultListDto> resultList = shopRepository.findAllByNameContaining(name)
                .stream()
                .sorted(Comparator.comparing(Shop::getFollower).reversed())
                .map(ShopSearchResultListDto::of)
                .collect(Collectors.toList());
        return resultList;
    }
}

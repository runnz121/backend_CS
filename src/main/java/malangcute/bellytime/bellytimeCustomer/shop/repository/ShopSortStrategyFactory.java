package malangcute.bellytime.bellytimeCustomer.shop.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShopSortStrategyFactory {

    private final List<ShopSortStrategy> shopSortStrategies;

    public ShopSortStrategy findStrategy(String type) {
        return shopSortStrategies.stream()
                .filter(strategy -> !Objects.isNull(type) && strategy.sortedBy(type))
                .findAny()
                .orElseThrow(() -> new NotFoundException("분류 기준이 없습니다"));
    }
}

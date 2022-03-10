package malangcute.bellytime.bellytimeCustomer.shop.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShopCoolTimeSearchStrategyFactory {

    public final List<ShopCoolTimeSearchStrategy> strategies;

    public ShopCoolTimeSearchStrategy searchStrategy(String filter) {
        return strategies.stream()
                .filter(strategy -> !Objects.isNull(filter) && strategy.filterBy(filter))
                .findAny()
                .orElseThrow(() -> new NotFoundException("분류기준이 없습니다(쿨타임)"));
    }
}

package malangcute.bellytime.bellytimeCustomer.feed.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FeedSortStrategyFactory {

    private final List<FeedSearchStrategy> strategies;

    public FeedSearchStrategy findStrategy(String filter) {

        return strategies.stream()
                .filter(strategy -> !Objects.isNull(filter) && strategy.filterBy(filter))
                .findAny()
                .orElseThrow(() -> new NotFoundException("분류 기준이 없습니다"));
    }
}

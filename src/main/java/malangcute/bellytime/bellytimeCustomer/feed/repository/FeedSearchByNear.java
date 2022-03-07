package malangcute.bellytime.bellytimeCustomer.feed.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedSearchByNear implements FeedSearchStrategy {

    private static final String TYPE = "nearby";

    private final FeedRepository feedRepository;

    @Override
    public List<FeedListResponse> selectedStrategy(User user, Long lat, Long lon, Pageable pageable) {
        return null;
    }

    @Override
    public boolean filterBy(String filter) {
        return TYPE.equals(filter);
    }
}

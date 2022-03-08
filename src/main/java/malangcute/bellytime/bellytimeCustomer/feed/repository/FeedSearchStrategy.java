package malangcute.bellytime.bellytimeCustomer.feed.repository;

import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedSearchStrategy {

    List<FeedListResponse> selectedStrategy(User user,  Double lat, Double lon,Pageable pageable);

    boolean filterBy(String filter);
}

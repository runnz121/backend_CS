package malangcute.bellytime.bellytimeCustomer.feed.service;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.feed.repository.FeedSearchByFollow;
import malangcute.bellytime.bellytimeCustomer.feed.repository.FeedSearchStrategy;
import malangcute.bellytime.bellytimeCustomer.feed.repository.FeedSortStrategyFactory;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final FeedSortStrategyFactory strategyFactory;


//    public List<FeedListResponse> getListBySub(User user, String filter, Pageable pageable) {
//
//        // 내가 팔로우하는 샵 아이디
//        return feedRepository.findByFilterWithSub(user.getId(), Pageable.ofSize(pageable.getPageSize()))
//                .stream().map(FeedListResponse::of)
//                .collect(Collectors.toList());
//    }

    public List<FeedListResponse> getListBy(User user, String filter, Long lat, Long lon, Pageable pageable) {

        System.out.println("===========================");
        System.out.println(filter + " 를 고름 ");


        List<FeedListResponse> lists = strategyFactory.findStrategy(filter).selectedStrategy(user, lat, lon, pageable);
        FeedSearchStrategy strategy = strategyFactory.findStrategy(filter);

        System.out.println(strategy.getClass());
        return lists;
    }

}

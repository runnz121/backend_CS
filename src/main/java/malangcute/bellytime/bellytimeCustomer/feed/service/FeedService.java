package malangcute.bellytime.bellytimeCustomer.feed.service;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.feed.domain.FeedImg;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedResultResponse;
import malangcute.bellytime.bellytimeCustomer.feed.repository.*;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final FeedSortStrategyFactory strategyFactory;

    private final FeedRepository feedRepository;

    public List<FeedListResponse> getListBy(User user, String filter, Double lat, Double lon, Pageable pageable) {
        return strategyFactory.findStrategy(filter).selectedStrategy(user, lat, lon, pageable);
    }

    public FeedResultResponse getFeedList(Long postId) {
        return feedRepository.findById(postId).stream()
                .map(FeedResultResponse::of)
                .findAny()
                .orElseThrow(() -> new NotFoundException("해당하는 포스터가 없습니다"));
    }
}

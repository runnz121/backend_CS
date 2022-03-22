package malangcute.bellytime.bellytimeCustomer.shop.repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

// 특별 기능 서술
@Component
@RequiredArgsConstructor
public class ShopSortByFollow implements ShopSortStrategy {

    private static final String TYPE = "follow";

    private final ShopRepository shopRepository;

    private final FollowService followService;

    private final CommentService commentService;



    @Override
    public boolean sortedBy(String type) {
        return TYPE.equals(type);
    }


    @Override
    public List<ShopSearchResultListResponse> SortedList(User user, String name) {
        return shopRepository.findAllByNameContaining(name)
                .stream()
                .sorted(Comparator.comparingInt(followService::shopFollower))
                .map(shop -> ShopSearchResultListResponse.of(shop,
                    commentService.reviewCountByShopId(shop),
                    followService.shopFollower(shop),
                    followService.checkStatus(shop),
                    followService.followStatusShop(user, shop)))
                .collect(Collectors.toList());
    }
}

package malangcute.bellytime.bellytimeCustomer.shop.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListWithMenuResponse;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopSortByScore implements ShopSortStrategy {

    private static final String TYPE = "bellscore";

    private final ShopRepository shopRepository;

    private final FollowService followService;

    private final CommentService commentService;



    @Override
    public boolean sortedBy(String type) {
        return TYPE.equals(type);
    }

    @Override
    public List<ShopSearchResultListWithMenuResponse> SortedList(User user, String name) {
        List<ShopSearchResultListWithMenuResponse> resultList = shopRepository.findAllByNameContaining(name)
                .stream()
                .sorted(Comparator.comparing(Shop::getBellscore).reversed())
                .map(shop -> ShopSearchResultListWithMenuResponse.of(shop,
                    commentService.reviewCountByShopId(shop),
                    followService.shopFollower(shop),
                    followService.checkStatus(shop),
                    followService.followStatusShop(user, shop)))
                .collect(Collectors.toList());
        return resultList;
    }
}

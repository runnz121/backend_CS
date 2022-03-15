package malangcute.bellytime.bellytimeCustomer.shop.repository;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeShopRecommendResponse;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopShopCoolTimeSearchByFollow implements ShopCoolTimeSearchStrategy {

    private final static String TYPE = "follow";

    private final ShopRepository shopRepository;

    private final CommentService commentService;

    private final FollowService followService;

    private final ShopService shopService;

    @Override
    public boolean filterBy(String filter) {
        return TYPE.equals(filter);
    }

    @Override
    public List<CoolTimeShopRecommendResponse> selectStrategy(User user, Long foodId, Double lat, Double lon, Pageable pageable) {
        return shopRepository.findByFilterWithFollow(user.getId(), foodId, pageable).stream()
                .map(shop -> CoolTimeShopRecommendResponse.of
                        (shop,  followService.shopFollower(shop),
                                commentService.reviewCountByShopId(shop),
                                shopService.checkStatus(shop)))
                .collect(Collectors.toList());
    }
}

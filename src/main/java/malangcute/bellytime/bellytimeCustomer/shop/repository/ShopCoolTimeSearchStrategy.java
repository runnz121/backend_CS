package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeShopRecommendResponse;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopCoolTimeSearchStrategy {

    boolean filterBy(String filter);

    List<CoolTimeShopRecommendResponse> selectStrategy(User user, Long foodId, Double lat, Double lon, Pageable page);
}

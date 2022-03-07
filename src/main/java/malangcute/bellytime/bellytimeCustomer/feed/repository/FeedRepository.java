package malangcute.bellytime.bellytimeCustomer.feed.repository;

import malangcute.bellytime.bellytimeCustomer.feed.domain.Feed;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query(" SELECT f FROM Feed AS f JOIN FETCH f.shop " +
            "WHERE f.shop.id in (SELECT fs.shop.id FROM FollowShop fs WHERE fs.user.id=:userId)")
    List<Feed> findByFilterWithSub(@Param("userId") Long userId, Pageable pageable);

//
//    @Query(" SELECT f FROM Feed AS f JOIN FETCH f.shop " +
//            "WHERE f.shop.id in (SELECT s.id , " +
//            "(6371 * acos(cos(radians(lat))*cos(radians(cafeLatitude))*cos(radians(cafeHardness) -radians(userHardness))+sin(radians(userLatitude))*sin(radians(cafeLatitude)))) AS distance " +
//
//            "FROM Shop s)")
//    Page<Feed> findByFilterWithNearBy( @Param("userId") Long userId,
//                                      @Param("lat") Long latitude,
//                                      @Param("lon") Long longitude, Pageable pageable);


//
//    @Query(nativeQuery = true, value = "SELECT (6371 * acos (cos(radians(lat))*cos(radians(cafeLatitude))*cos(radians(cafeHardness) - radians(userHardness))+sin(radians(userLatitude))*sin(radians(cafeLatitude)))) FROM Shop s")
//    List<Shop> check1 ( @Param("lat") Long latitude, @Param("lon")Long longitude);
}

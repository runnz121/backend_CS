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

    @Query(" SELECT f FROM Feed AS f " +
            "JOIN FETCH f.shop " +
            "WHERE f.shop.id in " +
            "(SELECT fs.shop.id " +
            "FROM FollowShop fs " +
            "WHERE fs.user.id=:userId)")
    List<Feed> findByFilterWithSub(@Param("userId") Long userId, Pageable pageable);



    @Query(nativeQuery = true,
            value = "SELECT * FROM feed_post WHERE id in " +
                    "(SELECT id FROM shop sh WHERE " +
                    "(6371 * acos (cos(radians(:lat)) " +
                    "* cos(radians(sh.latitude)) " +
                    "* cos(radians(sh.longitude) " +
                    "- radians(:lon)) " +
                    "+ sin(radians(:lat)) " +
                    "* sin(radians(sh.latitude)))) < 2 )")
    Page<Feed> findByFilterWithNearBy(  @Param("lat") double latitude,
                                       @Param("lon") double longitude, Pageable pageable);

}

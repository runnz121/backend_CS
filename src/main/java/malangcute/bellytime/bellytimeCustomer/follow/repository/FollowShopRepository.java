package malangcute.bellytime.bellytimeCustomer.follow.repository;

import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.follow.dto.getFollowShopList;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowShopRepository extends JpaRepository<FollowShop, Long> {

//    @Query("SELECT s.id AS shopId, s.name AS shopName, s.image AS profileImg FROM FollowShop fs LEFT JOIN FETCH Shop s ON fs.shop.id = s.id WHERE fs.user.id=:userId ")
//    List<getFollowShopList> findMyFollowShopById(@Param("userId") Long id);

    //유저가 팔로우한 가게 갖고오기
//    @Query("SELECT distinct fs FROM FollowShop fs JOIN FETCH fs.shop JOIN FETCH fs.user WHERE fs.user.id=:userId ")
//    List<FollowShop> findMyFollowShopById(@Param("userId") Long Id);

    @EntityGraph(attributePaths = {"user", "shop"})
    @Query("SELECT distinct fs FROM FollowShop fs WHERE fs.user.id=:userId ")
    List<FollowShop> findMyFollowShopById(@Param("userId") Long Id);


    //유저가 선택한 팔로우된 가게 지우기
    @Modifying
    @Query("DELETE FROM FollowShop fs WHERE fs.user.id=:userId AND fs.shop.id=:shopId ")
    void deleteFollowShopId(@Param("userId") Long id, @Param("shopId") Long shopId);

    List<FollowShop> findByUserId(User user);
}

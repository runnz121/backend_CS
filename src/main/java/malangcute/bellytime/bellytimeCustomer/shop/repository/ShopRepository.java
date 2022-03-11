package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopDetailDto;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopResultDto;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

//jpa 문법 사용
public interface ShopRepository extends JpaRepository<Shop, Long> {


//    @Query(nativeQuery = true,
//            value = " select * from shop sh where sh.id in (select * from (select fs.shop_id from follow_shop fs group by fs.shop_id order by count(fs.shop_id ) desc limit :param) AS pp) order by bellscore desc")
//    Page<Shop> findPopularTop3Shop(@Param("param")Long param, Pageable pageable);

    // @Query(nativeQuery = true,
    //         value = "select * from shop where id in (select * from "
    //               + "(select fs.shop_id from follow_shop fs group by fs.shop_id order by "
    //               + "count(fs.shop_id ) desc limit 3) AS pp) order by bellscore desc ")
    // Page<Shop> findPopularTop3Shop(Pageable pageable);


    @Query(nativeQuery = true,
            value = "SELECT * FROM shop s INNER JOIN "
                  + "(SELECT fs.shop_id, count(fs.shop_id) "
                  + "FROM follow_shop fs GROUP BY fs.shop_id ) AS c "
                  + "ON s.id = c.shop_id ORDER BY c.shop_id DESC")
    Page<Shop> findPopularTop3Shop(Pageable pageable);


    List<ShopResultDto> findByNameContaining(final String name);


    List<Shop> findAllByNameContaining(String name);

    @Query(nativeQuery = true,
           value =  "SELECT * FROM shop sh WHERE " +
                    "(6371 * acos (cos(radians(:lat)) " +
                    "* cos(radians(sh.latitude)) " +
                    "* cos(radians(sh.longitude) " +
                    "- radians(:lon)) " +
                    "+ sin(radians(:lat)) " +
                    "* sin(radians(sh.latitude)))) < 2 " +
                    "AND sh.id IN (SELECT sm.shop_id FROM shop_menu sm " +
                    "WHERE sm.food_id=:foodId)")
    Page<Shop> findByFilterWithNearBy(  @Param("foodId") Long foodId,
                                       @Param("lat") double latitude,
                                       @Param("lon") double longitude, Pageable pageable );


    @Query(nativeQuery = true,
            value = "SELECT * FROM shop WHERE shop.id IN " +
                    "(SELECT sm.shop_id FROM shop_menu sm " +
                    "WHERE sm.food_id=:foodId)" +
                    "AND shop.id IN (SELECT fs.shop_id FROM " +
                    "follow_shop fs WHERE fs.user_id=:userId)")
    Page<Shop> findByFilterWithFollow(   @Param("userId") Long userId,
                                        @Param("foodId") Long foodId, Pageable pageable);


    @Query(nativeQuery = true,
            value = "SELECT * FROM shop sh WHERE sh.id IN (SELECT " +
                    "fs.id FROM follow_shop fs WHERE fs.user_id=:userId)")
    Page<Shop> findMyFollowShopById(@Param("userId") Long id, Pageable pageable);
}

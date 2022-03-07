package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.List;

//jpa 문법 사용
public interface ShopRepository extends JpaRepository<Shop, Long> {


    @Query(nativeQuery = true, value = " select * from shop sh where sh.id in (select * from (select fs.shop_id from follow_shop fs group by fs.shop_id order by count(fs.shop_id ) desc limit :param) AS pp) order by bellscore desc")
    Page<Shop> findPopularTop3Shop(@Param("param")Long param, Pageable pageable);

//    @Query("SELECT sh FROM Shop sh JOIN FETCH sh.followShops WHERE sh.id IN (SELECT fs.shop.id FROM )")
//    Page<Shop> findPop(Pageable pageable);


    List<ShopResultDto> findByNameContaining(final String name);


    List<Shop> findAllByNameContaining(String name);



    //전체쿼리
//    @Query(nativeQuery = true, value =
//            "SELECT s.id AS id, s.name AS name, s.image AS img, s.bellscore AS bellscore, s.latitude AS latitude, " +
//                    "s.longitude AS longitude, s.address AS adddress, s.runtime AS runtime, (SELECT COUNT(*) FROM follow_shop m WHERE m.shop_id = s.id) AS followerCount, sm.menu AS menu " +
//                    "FROM shop_menu sm RIGHT JOIN shop s  ON sm.shop_id = s.id WHERE sm.shop_id in (SELECT s.id from shop s where s.name=?1) ")
//    List<ShopDetailDto> findNameDetail(String name);



    //shop 만 조회하는 쿼리
//    @Query(nativeQuery = true, value =
//            "SELECT s.id AS id, s.name AS name, s.image AS image, s.bellscore AS bellscore, s.latitude AS latitude, " +
//                    "s.longitude AS longitude, s.address AS address, s.runtime AS runtime, (SELECT COUNT(*) FROM follow_shop m WHERE m.shop_id = s.id) AS followerCount " +
//                    "FROM Shop s WHERE s.name=?1 ")
//   // List<Shop> findNameDetail(String name);
//    List<ShopDetailDto> findNameDetail(String name);


//    @Query( "SELECT sh FROM Shop sh " +
//            "WHERE sh.id in " +
//            "(SELECT fs.shop.id FROM FollowShop fs " +
//            "GROUP BY fs.shop.id " +
//            "ORDER BY COUNT(fs.shop.id) DESC) ")
//    List<Shop> findPopularTop3Shop(Pageable pageable);
//






//    //메뉴만 조회화는 쿼리
//    @Query(nativeQuery = true, value = "SELECT s.id AS id, sm.menu AS menu FROM shop_menu sm RIGHT JOIN shop s ON sm.shop_id=s.id WHERE sm.shop_id in (SELECT s.id from shop s where s.id=?1) ")
//    List<ShopMenu> findMenus(Long id);
//
//
//    //인기 3개만 갖고오기
//    @Query(nativeQuery = true, value = "select * from shop sh where id in (select fs.shop_id AS pp from follow_shop fs group by fs.shop_id order by count(fs.shop_id ) desc) limit 3")
//    List<Shop> findPopularTop3Shop();

}

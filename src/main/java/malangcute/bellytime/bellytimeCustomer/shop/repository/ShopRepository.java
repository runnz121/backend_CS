package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.domain.ShopMenu;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopDetailDto;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//jpa 문법 사용
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {


    List<ShopResultDto> findByNameContaining(final String name);



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



    List<Shop> findAllByName(String name);




    //메뉴만 조회화는 쿼리
    @Query(nativeQuery = true, value = "SELECT s.id AS id, sm.menu AS menu FROM shop_menu sm RIGHT JOIN shop s ON sm.shop_id=s.id WHERE sm.shop_id in (SELECT s.id from shop s where s.id=?1) ")
    List<ShopMenu> findMenus(Long id);

}

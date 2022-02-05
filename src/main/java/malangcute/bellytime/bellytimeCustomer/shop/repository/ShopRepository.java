package malangcute.bellytime.bellytimeCustomer.shop.repository;

import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

//jpa 문법 사용
public interface ShopRepository extends JpaRepository<Shop, Long> {
}

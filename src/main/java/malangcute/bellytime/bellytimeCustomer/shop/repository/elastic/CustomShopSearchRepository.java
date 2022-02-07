package malangcute.bellytime.bellytimeCustomer.shop.repository.elastic;

import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomShopSearchRepository {

    //복잡한 쿼리를 생성하기 위해 선언 및 구현
    //List<Shop> searchByName(String name, Pageable pageable);

    List<Shop> searchByName(String name);
}

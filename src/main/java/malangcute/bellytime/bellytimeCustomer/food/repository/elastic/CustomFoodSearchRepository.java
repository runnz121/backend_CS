package malangcute.bellytime.bellytimeCustomer.food.repository.elastic;

import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

import java.util.List;

public interface CustomFoodSearchRepository {

    //복잡한 쿼리를 생성하기 위해 선언 및 구현
    //List<Shop> searchByName(String name, Pageable pageable);

    List<Food> searchByName(String name);
}

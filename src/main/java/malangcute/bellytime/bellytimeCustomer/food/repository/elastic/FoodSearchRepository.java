package malangcute.bellytime.bellytimeCustomer.food.repository.elastic;

import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

//@NoRepositoryBean
public interface FoodSearchRepository extends ElasticsearchRepository<Food,Long>, CustomFoodSearchRepository {
//
//    List<FoodResultDto> findByNameContaining(String findFood);
//
//    Optional<Food> findByName(String foodName);
}

package malangcute.bellytime.bellytimeCustomer.food.repository;

import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<FoodResultDto> findByNameContaining(final String findFood);

    //List<Food> findByNameContaining(final String findFood);

    Optional<Food> findByName(final String foodName);
}

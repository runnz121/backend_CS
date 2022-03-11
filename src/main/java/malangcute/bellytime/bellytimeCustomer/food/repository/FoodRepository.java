package malangcute.bellytime.bellytimeCustomer.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<FoodResultDto> findByNameContaining(final String findFood);


    Optional<Food> findByName(final String foodName);

    @Query("SELECT fd FROM Food fd JOIN CoolTime ct "
        + "ON fd.id = ct.foodId.id WHERE ct.userId.id=:userId ")
    List<Food> findByFoodWithUser(@Param("userId") Long userId);
}

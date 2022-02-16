package malangcute.bellytime.bellytimeCustomer.food.repository;

import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<FoodResultDto> findByNameContaining(final String findFood);

    //List<Food> findByNameContaining(final String findFood);

    Optional<Food> findByName(final String foodName);

    //유저아이디를 통해서 해당 유저아이디기 존재하는 쿨타임에 존재하는 푸드를 모두 갖고오기
    @Query("SELECT fd FROM Food fd JOIN CoolTime ct ON fd.id = ct.foodId.id WHERE ct.userId.id=:userId ")
    List<Food> findByFoodWithUser(@Param("userId") Long userId);
}

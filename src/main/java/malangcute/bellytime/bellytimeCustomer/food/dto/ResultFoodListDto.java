package malangcute.bellytime.bellytimeCustomer.food.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultFoodListDto {

    private List<String> resultList;

    public static ResultFoodListDto of (Food resultList) {
        return new ResultFoodListDto();
    }
}

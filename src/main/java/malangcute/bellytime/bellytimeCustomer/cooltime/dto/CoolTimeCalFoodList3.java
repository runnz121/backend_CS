package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CoolTimeCalFoodList3 {

    private String foodName;

    private Long foodId;

    private String foodImg;


    public static CoolTimeCalFoodList3 of (Food food) {
        return new CoolTimeCalFoodList3(food.getName(), food.getId(), food.getImage());
    }

    public static CoolTimeCalFoodList3 from (GetMyCoolTimeListIF list) {
        return new CoolTimeCalFoodList3(
                list.getFoodName(),
                Long.valueOf(list.getFoodId()),
                list.getFoodImg()
        );
    }
}

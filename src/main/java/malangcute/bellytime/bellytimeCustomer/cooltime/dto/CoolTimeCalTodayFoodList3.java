package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CoolTimeCalTodayFoodList3 {

    private String foodName;

    private Long foodId;

    private String foodImg;

    private boolean eat;



    public static CoolTimeCalTodayFoodList3 of (Food food, CoolTime coolTime) {
        return new CoolTimeCalTodayFoodList3(food.getName(), food.getId(), food.getImage(), coolTime.getEat());
    }

    public static CoolTimeCalTodayFoodList3 from (GetMyCoolTimeListIF list) {
        return new CoolTimeCalTodayFoodList3(
                list.getFoodName(),
                Long.valueOf(list.getFoodId()),
                list.getFoodImg(),
                list.getEat()
        );
    }
}

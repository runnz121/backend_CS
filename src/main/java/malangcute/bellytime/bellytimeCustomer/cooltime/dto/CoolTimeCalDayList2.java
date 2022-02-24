package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//datalist안에

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeCalDayList2 {

    private int day;

    private List<CoolTimeCalFoodList3> data = new ArrayList<>();

    public static CoolTimeCalDayList2 of (int day, List<CoolTimeCalFoodList3> list3) {
        return new CoolTimeCalDayList2(day, list3);
    }

}

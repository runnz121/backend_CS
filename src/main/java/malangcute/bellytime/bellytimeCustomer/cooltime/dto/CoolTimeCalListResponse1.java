package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeCalListResponse1 {

    private List<CoolTimeCalDayList2> dateList = new ArrayList<>();

    private List<CoolTimeCalTodayFoodList3> today;


    public static CoolTimeCalListResponse1 of(List<CoolTimeCalDayList2> dateList,
                                              List<CoolTimeCalTodayFoodList3> todayList) {
         return new CoolTimeCalListResponse1(dateList, todayList);
    }
}

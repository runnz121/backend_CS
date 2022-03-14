package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;


// 최상단 트리
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeCalListResponse1 {

    private List<CoolTimeCalDayList2> dateList = new ArrayList<>();

    private List<CoolTimeCalTodayFoodList2> today = new ArrayList<>();



     public void addList (CoolTimeCalDayList2 list) {

         dateList.add(list);
    }

    public void addToday (CoolTimeCalTodayFoodList2 list) {
         today.add(list);
    }
}

package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetMyCoolTimeList {

    private String foodId;

    private String foodName;

    private String foodImg;

    private String gauge;

    // 만기예정일
    private LocalDateTime startDate;

    // 현재 날짜 기준으로 남은 일
    private LocalDateTime endDate;
}

package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetMyCoolTimeList {

    private String foodId;

    private String foodName;

    private String gauge;

    private String foodImg;

    private String startDate;

    private Integer duration;
    // 만기예정일
    private  String predictDate;
    // 현재 날짜 기준으로 남은 일
    private  Long leftDays;

    public static GetMyCoolTimeList of(GetMyCoolTimeListIF myCoolTime, String startDate, String predictDate, Long leftDays) {
        return new GetMyCoolTimeList(
            myCoolTime.getFoodId(),
            myCoolTime.getFoodName(),
            myCoolTime.getGauge(),
            myCoolTime.getFoodImg(),
            startDate,
            myCoolTime.getDuration(),
            predictDate,
            leftDays
        );
    }
}


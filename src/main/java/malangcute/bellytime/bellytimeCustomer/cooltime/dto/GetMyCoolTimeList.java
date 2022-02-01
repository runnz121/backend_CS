package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
//@Builder(builderMethodName = "create")
public class GetMyCoolTimeList {

    private String foodId;

    private  String name;

    private  String gauge;

    private  String foodImg;

    // 만기예정일
    private  LocalDateTime predictDate;
    // 현재 날짜 기준으로 남은 일
    private  Long leftDays;



//    public static GetMyCoolTimeList create(GetMyCoolTimeListIF list) {
//        return new GetMyCoolTimeList(
//                list.getFoodId(),
//                list.getFoodName(),
//                list.getGauge(),
//                list.getFoodImg(),
//                list.getEndDate(),
//                list.getEndDate()
//        );
//    }

//    @Builder
//    public GetMyCoolTimeList(String foodId, String name, String gauge, String foodImg, LocalDateTime predictDate, Long leftDays) {
//        this.foodId = foodId;
//        this.name = name;
//        this.gauge =gauge;
//        this.foodImg = foodImg;
//        this.predictDate = predictDate;
//        this.leftDays = leftDays;
    }


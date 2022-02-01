package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
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


//    public static GetMyCoolTimeListBuilder builder(){
//        return create();
//    }


}

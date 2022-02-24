package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyListResponse {

    private String foodId;

    private String foodName;

    private String foodImg;

    private String gauge;

    // 만기예정일
    private LocalDateTime predictDate;

    // 현재 날짜 기준으로 남은 일
    private LocalDateTime leftDays;


//    @Builder
//    public static MyListResponse of(CoolTime item) {
//        item.getId(),
//        item.get
//
//    }
}

package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import java.time.LocalDateTime;

public interface GetMyCoolTimeListIF {
    String getFoodId();
    String getFoodName();
    String getFoodImg();
    String getGauge();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
}

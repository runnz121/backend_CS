package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import org.apache.kafka.common.protocol.types.Field;

import java.time.LocalDateTime;

public interface GetMyCoolTimeListIF {
    Long getFoodId();

    String getFoodName();

    String getFoodImg();

    String getGauge();

    Integer getDuration();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    Boolean getEat();
}

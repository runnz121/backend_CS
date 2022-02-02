package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CoolTimeSettingRequest {

    private Long foodId;

    private String foodName;

    private String startDate;

    private Integer duration;
}

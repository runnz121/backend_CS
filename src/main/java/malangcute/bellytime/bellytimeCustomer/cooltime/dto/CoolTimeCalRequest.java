package malangcute.bellytime.bellytimeCustomer.cooltime.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoolTimeCalRequest {
    private Long year;

    private Long month;
}

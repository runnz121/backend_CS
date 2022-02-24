package malangcute.bellytime.bellytimeCustomer.cooltime.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CoolTimeCalRequest {
    private Long year;

    private Long month;
}

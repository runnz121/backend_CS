package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeCheckRequest {

    private Long foodId;

    private boolean eat;
}

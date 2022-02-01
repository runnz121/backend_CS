package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class DeleteCoolTimeRequest {

    private Long foodId;
}

package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class SearchResultResponse {

    private Long foodId;

    private String foodName;

}

package malangcute.bellytime.bellytimeCustomer.follow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateFollowList {

    private Long userId;
    private Long shopId;


    public static CreateFollowList from (Long userId, Long shopId) {
        return new CreateFollowList(userId, shopId);
    }
}

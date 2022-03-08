package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoolTimeGetMyFriends {

    private Long friendId;

    private String name;

    private String gauge;

    private String profileImg;

    public static CoolTimeGetMyFriends of (CoolTimeGetMyFriendsIF result) {
        return new CoolTimeGetMyFriends(
                result.getFriendId(),
                result.getName(),
                result.getGauge(),
                result.getProfileImg()
        );
    }
}

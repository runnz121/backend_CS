package malangcute.bellytime.bellytimeCustomer.follow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyFriendListResponse {

    private Long contactId;

    private String nickName;

    private String profileImg;

    public static MyFriendListResponse from(FollowUser followUser) {
        return new MyFriendListResponse(
                followUser.getFriendId().getId(),
                followUser.getFriendId().getNickname().getNickName(),
                followUser.getFriendId().getProfileImg());
    }
}

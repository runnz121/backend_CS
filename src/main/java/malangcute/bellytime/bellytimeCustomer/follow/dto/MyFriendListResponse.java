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

    // 나의 친구 아이디(내거 말고)
    private Long id;

    private String name;

    private String profileImg;

    public static MyFriendListResponse from(FollowUser followUser) {
        return new MyFriendListResponse(
                followUser.getFriendId().getId(),
                followUser.getFriendId().getUsername(),
                followUser.getFriendId().getProfileImg());
    }

}

package malangcute.bellytime.bellytimeCustomer.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserProfileResponse {

    private String profileImg;

    private String name;

    public static UserProfileResponse of (User user) {
        return new UserProfileResponse(user.getProfileImg(), user.getUsername());
    }
}

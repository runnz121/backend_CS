package malangcute.bellytime.bellytimeCustomer.follow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyFriendSearchResponse {

    private Long id;

    private String nickName;

    private String profileImg;

    public static MyFriendSearchResponse from (User user) {
        return new MyFriendSearchResponse(
                user.getId(),
                user.getNickname().getNickName(),
                user.getProfileImg()
        );
    }

}

package malangcute.bellytime.bellytimeCustomer.user.controller;

import malangcute.bellytime.bellytimeCustomer.config.RestDocsSupport;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("유저 컨트롤러 테스트")
class UserControllerTest extends RestDocsSupport {

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AwsS3uploader awsS3uploader;




    @DisplayName("유저 사진 업데이트")
    @Test
    void update() {

    }
    @DisplayName("유저 사진 업데이트")
    @Test
    void getMyProfile() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void myFollowShopList() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void toFollowShop() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void toUnFollowShop() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void myFriendList() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void findMyFriend() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void followMyFriends() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void unFollowMyFriend() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void myCoolTimeCalender() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void checkMyCoolTime() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void myReservationList() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void getMyCommentList() {
    }

    @DisplayName("유저 사진 업데이트")
    @Test
    void writeComment() {
    }
}
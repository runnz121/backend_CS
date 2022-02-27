package malangcute.bellytime.bellytimeCustomer.user.controller;

import malangcute.bellytime.bellytimeCustomer.BaseSetup;
import malangcute.bellytime.bellytimeCustomer.global.aws.AwsS3uploader;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("유저 컨트롤러 테스트")
class UserControllerTest extends BaseSetup {

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AwsS3uploader awsS3uploader;

    @BeforeEach
    public void setUpUser() {
        user = User.builder()
                .id(1L)
                .passWord("test")
                .email("test@test.com")
                .nickName("testnickname")
                .profileImg("cloudfront_img")
                .phoneNumber("010-0000-0000")
                .build();
        userRepository.save(user);
    }


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
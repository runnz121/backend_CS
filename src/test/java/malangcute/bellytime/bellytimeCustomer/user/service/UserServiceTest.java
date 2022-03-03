package malangcute.bellytime.bellytimeCustomer.user.service;

import malangcute.bellytime.bellytimeCustomer.config.RestDocsSupport;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("유저 컨트롤러 테스트")
class UserServiceTest extends RestDocsSupport {

    @Autowired
    private UserRepository userRepository;


    private User user;

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

    @DisplayName("유저 아이디로 유저 찾기")
    @Test
    void findUserById() {
    }

    @DisplayName("유저 이메일로 유저 찾기(유저반환)")
    @Test
    void findUserByEmail() {
    }

    @DisplayName("나의 프로필 찾기")
    @Test
    void userProfile() {
    }

    @DisplayName("유저 정보 업데이트")
    @Test
    void userUpdate() {
    }

    @DisplayName("프로필 이미지 업데이트")
    @Test
    void updateImg() {
    }

    @DisplayName("닉네임으로 친구 찾기")
    @Test
    void findUserByNickname() {
    }

    @DisplayName("유저 로그아웃")
    @Test
    void userLogOut() {
    }
}
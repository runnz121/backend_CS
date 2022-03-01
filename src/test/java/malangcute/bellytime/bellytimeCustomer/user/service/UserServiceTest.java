package malangcute.bellytime.bellytimeCustomer.user.service;

import malangcute.bellytime.bellytimeCustomer.BaseSetup;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("유저 컨트롤러 테스트")
class UserServiceTest extends BaseSetup {

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

    @DisplayName("유저 컨트롤러 테스트")
    @Test
    void findUserById() {
    }

    @DisplayName("유저 컨트롤러 테스트")
    @Test
    void findUserByEmail() {
    }

    @DisplayName("유저 컨트롤러 테스트")
    @Test
    void userProfile() {
    }

    @Test
    void userUpdate() {
    }

    @Test
    void updateImg() {
    }

    @Test
    void findUserByNickname() {
    }

    @Test
    void userLogOut() {
    }
}
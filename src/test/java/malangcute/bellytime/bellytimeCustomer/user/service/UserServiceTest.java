package malangcute.bellytime.bellytimeCustomer.user.service;

import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserIdResponse;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginService userService;

    @DisplayName("유저 저장 및 아이디 조회 확인")
    @Test
    void findById() {

        //given
        User user = User.builder()
                .id(123L)
                .nickName("here")
                .email("adsf@adsf.com")
                .passWord("AaaAbb11!23")
                .phoneNumber("010-2282-9999")
                .profileImg("adsfasdf1A!adf")
                .build();

        userRepository.save(user);
        //when
        UserIdResponse result = userService.findById(123L);


        //then
        Assertions.assertThat(result.getId()).isEqualTo(123L);
    }
}
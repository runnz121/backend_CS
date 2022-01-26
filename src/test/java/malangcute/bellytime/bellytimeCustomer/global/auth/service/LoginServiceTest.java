package malangcute.bellytime.bellytimeCustomer.global.auth.service;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.exception.UserEmailException;
import malangcute.bellytime.bellytimeCustomer.user.domain.Email;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class LoginServiceTest {

//    Logger log = (Logger) LoggerFactory.getLogger(LoginServiceTest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void findById() {
    }

    @Test
    void validUser() {
    }

    @Test
    void registerNewUser() {
    }

    @DisplayName("유저이메일 체크")
    // 이메일 객체를 생성해서 넣어줘야했음
    @Test
    void checkEmail() throws Exception {

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
        User result = userRepository.findByEmail(new Email("adsf@adsf.com")).orElseThrow(()->new UserEmailException("이메일이 없음"));

        //then
        Assertions.assertThat(result.getEmail().getEmail()).isEqualTo("adsf@adsf.com");
    }


    @DisplayName("유저 비밀번호 체크")
    @Test
    void checkPassword() {

        //given
        User user = User.builder()
                .id(123L)
                .nickName("here")
                .email("adsf@adsf.com")
                .passWord(passwordEncoder.encode("AaaAbb11!23"))
                .phoneNumber("010-2282-9999")
                .profileImg("adsfasdf1A!adf")
                .build();

        userRepository.save(user);


        //when
//        String one = passwordEncoder.encode(user.getPassword());
        String one = user.getPassword();
        String two = loginService.checkEmail("adsf@adsf.com").getPassword();


        Assertions.assertThat(one).isEqualTo(two);
    }
}
package malangcute.bellytime.bellytimeCustomer.global.auth;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import javax.persistence.EntityManager;

@DisplayName("토큰 생성 테스트")
@SpringBootTest
@Transactional
@Slf4j
class TokenProviderTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUser() {
        user = User
                .builder()
                .nickName("종빈")
                .email("runna121@gmail.com")
                .passWord("test")
                .phoneNumber("010-1234-1234")
                .profileImg("cats.jpeg")
                .build();
        userRepository.save(user);
    }

    @Test
    void createRefreshToken() {
    }

    @DisplayName("유저 이메일로 엑세스 토큰을 생성한다")
    @Test
    void createAccessTokenTest() {
        String accessToken = tokenProvider.createAccessToken(user.getEmail().getEmail());
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail().getEmail());

        assertThat(tokenProvider.createAccessToken(user.getEmail().getEmail())).isNotEmpty();

        assertThatCode(() -> tokenProvider.validateAccessToken(accessToken,refreshToken)).doesNotThrowAnyException();
    }

    @Test
    void generateJwt() {
    }

    @Test
    void validateRefreshToken() {
    }

    @Test
    void validateAccessToken() {
    }

    @Test
    void getUserIdFromToken() {
    }
}